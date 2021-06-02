package application;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageTableCell<T> extends TableCell<T, Boolean>  {
    private final ImageView imageView;
    private final Image trueImage;
    private final Image falseImage;

    public ImageTableCell(Image trueImage, Image falseImage) {
        imageView = new ImageView();
        setGraphic(imageView);
        this.trueImage = trueImage;
        this.falseImage = falseImage;
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            imageView.setImage(null);
        } else {
            imageView.setImage(item ? trueImage : falseImage);
        }
    }
}

