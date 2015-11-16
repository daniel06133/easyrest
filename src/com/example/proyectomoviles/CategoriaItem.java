package com.example.proyectomoviles;
public class CategoriaItem {
    private String image;
    private String title;

    public CategoriaItem() {
        super();
    }
    public CategoriaItem(String urlImagen,String descripcion)
    {	
    	super();
    	image = urlImagen;
    	title = descripcion;    	
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
