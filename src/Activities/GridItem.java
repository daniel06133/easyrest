package Activities;
public class GridItem {
    private String image;
    private String title;

    public GridItem() {
        super();
    }
    public GridItem(String urlImagen,String descripcion)
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
