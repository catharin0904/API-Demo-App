package com.catharin.apidemo;



import static com.catharin.apidemo.MainActivity.image;
import static com.catharin.apidemo.MainActivity.title;

public class dialogBox {
    private static final dialogBox ourInstance = new dialogBox();
    public static dialogBox getInstance() {
        return ourInstance;
    }
    private dialogBox() { }

    public void showDialogBox(String imageURL, String title_api){
        LoadImage loadImage = new LoadImage(image);
        loadImage.execute(imageURL);

        title.setText(title_api);

    }

}
