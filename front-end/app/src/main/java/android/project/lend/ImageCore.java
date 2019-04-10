package android.project.lend;

public class ImageCore {

    private Integer id;
    private Integer productId;
    private String url;

    public Boolean changed;
    public Boolean changedProductId;
    public Boolean changedUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {

        if(this.productId == productId) return;

        changed = true;
        changedProductId = true;

        this.productId = productId;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) {

        if(this.url == url) return;

        changed = true;
        changedUrl = true;

        this.url = url;
    }

    public void create(){

    }

    public void update(){

        if(!changed) return;

        if(changedProductId){}
    }

    public void delete(){

    }

    public void clearChanges(){

        changed = false;
        changedProductId = false;
    }
}
