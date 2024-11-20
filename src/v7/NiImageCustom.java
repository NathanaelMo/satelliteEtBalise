package v7;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import nicellipse.component.NiImage;
import java.io.File;
import java.io.IOException;

public class NiImageCustom extends NiImage {
    private BufferedImage image;
    private BufferedImage imageSync;
    private boolean afficherSync = false;
    private double angleRotation = 0;
    private final int targetWidth;
    private final int targetHeight;
    private static final float SYNC_EFFECT_ALPHA = 0.7f;
    private static final int SYNC_EFFECT_SCALE = 4; // Augmenté de 2 à 4
    private boolean estEnSync = false;

    public NiImageCustom(String cheminImage, String cheminImageSync, int width, int height) throws IOException {
        super(ImageIO.read(new File(cheminImage)));
        this.targetWidth = width;
        this.targetHeight = height;

        BufferedImage originalImage = ImageIO.read(new File(cheminImage));
        this.image = resizeImage(originalImage, width, height);

        if (cheminImageSync != null) {
            BufferedImage originalSyncImage = ImageIO.read(new File(cheminImageSync));
            int syncWidth = width * SYNC_EFFECT_SCALE;
            int syncHeight = height * SYNC_EFFECT_SCALE;
            this.imageSync = resizeImage(originalSyncImage, syncWidth, syncHeight);
        }

        this.setSize(width * SYNC_EFFECT_SCALE, height * SYNC_EFFECT_SCALE);
    }

    private BufferedImage resizeImage(BufferedImage original, int width, int height) {
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(original, 0, 0, width, height, null);
        g2d.dispose();
        return resized;
    }

    public void setAfficherSync(boolean afficher) {
        this.afficherSync = afficher;
        if (!afficher) {
            angleRotation = 0;
            estEnSync = false;
        } else {
            estEnSync = true;
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (afficherSync && imageSync != null) {
            Composite oldComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, SYNC_EFFECT_ALPHA));

            if (!estEnSync) {
                g2d.rotate(angleRotation, getWidth()/2, getHeight()/2);
                angleRotation += 0.1;
                if (angleRotation >= 2 * Math.PI) {
                    angleRotation = 0;
                }
                repaint();
            }
            g2d.drawImage(imageSync, 0, 0, null);
            g2d.setComposite(oldComposite);
        }

        int x = (getWidth() - targetWidth) / 2;
        int y = (getHeight() - targetHeight) / 2;
        g2d.drawImage(image, x, y, targetWidth, targetHeight, null);

        g2d.dispose();
    }
}