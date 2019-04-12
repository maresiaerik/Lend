package android.project.lend;

public class LendzCore {

    private Integer id;
    private String startDate;
    private String dueDate;
    private Integer rating;

    public Boolean changed;
    public Boolean changed_startDate;
    public Boolean changed_dueDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {

        if(this.startDate == startDate) return;

        changed = true;
        changed_startDate = true;

        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {

        if(this.dueDate == dueDate) return;

        changed = true;
        changed_dueDate = true;

        this.dueDate = dueDate;
    }

    public void create(){

    }

    public void update(){

        if(!changed) return;

        if(changed_startDate){}
        if(changed_dueDate){}
    }

    public void delete(){

    }

    public void clearChanges(){

        changed = false;
        changed_startDate = false;
        changed_dueDate = false;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        if(this.rating == rating) return;

        changed = true;
        changed_dueDate = true;
        this.rating = rating;
    }
}
