package com.tilf.troke.service;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.repository.CustomUserRepository;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
/**
 * Created by Manu on 2015-10-25.
 */
@Service
public class ImageService {

    @Autowired
    private CustomUserRepository customUserRepository;
    @Autowired
    private AuthUserContext authContext;

    public static byte[] scale(byte[] fileData, int width, int height) {
        ByteArrayInputStream in = new ByteArrayInputStream(fileData);
        try {
            BufferedImage img = ImageIO.read(in);
            if (height == 0) {
                height = (width * img.getHeight()) / img.getWidth();
            }
            if (width == 0) {
                width = (height * img.getWidth()) / img.getHeight();
            }
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, "jpg", buffer);

            byte[] resizedBytes = buffer.toByteArray();
            buffer.close();

            return resizedBytes;
        } catch (IOException e) {
        }
        return null;
    }

    public boolean uploadImage(MultipartFile avatar, String imageName, boolean isPermanentImage, HttpSession session) {
        String imagePath;
        if (isPermanentImage) {
            imagePath = "src/main/resources/static/uploaded-images/" ;
        } else {
            imagePath = "src/main/resources/static/uploaded-images/temp/";
        }

        if (avatar != null && !avatar.isEmpty()) {
            try {
                byte[] bytes = avatar.getBytes();
                byte[] resizedBytes = new byte[0];
                // Vérification de la taille de l'image
                InputStream in = new ByteArrayInputStream(bytes);
                BufferedImage buf = ImageIO.read(in);
                float width = (float) buf.getWidth();
                float height = (float) buf.getHeight();
                float calculHeight = 800 / (width / height);
                // On arrondie pour permettre à la méthode scale de recevoir un int
                int modifier = (int) Math.ceil(calculHeight);
                // Resize byte if ration isn't 1:1
                if (width / height != 1) {
                    if ((width / height) < 1) {
                        resizedBytes = scale(bytes, modifier, modifier);
                    } else if ((width / height) > 1) {
                        resizedBytes = scale(bytes, 800, modifier);
                    }
                } else {
                    // Resize byte if ratio is 1:1
                    resizedBytes = scale(bytes, 800, 800);
                }
                // On affecte le nom et le path pour uploader l'image
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(imagePath + imageName)));
                // On upload l'image
                stream.write(resizedBytes);
                stream.close();
                // Si l'upload a eu lieu
                return true;
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
                System.out.println(ioe.fillInStackTrace());
            }
        } else if (avatar.isEmpty() && session.getAttribute("avatarfile") != null) {

            File tempDirectory = new File("C:\\Users\\Manu\\Documents\\GitHub\\troke\\src\\main\\resources\\static\\uploaded-images\\temp");
            try {
                String path = new File(".").getCanonicalPath();
                FileUtils.cleanDirectory(tempDirectory);
                System.out.println(path);
            }
            catch(IOException e){
            }
            return true;
        }
        // Si l'upload n'a pas eu lieu
        return false;
    }
}
