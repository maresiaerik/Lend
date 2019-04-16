package android.project.lend;

public class LendzCore {

    private Integer id;
    private String startDate;
    private String dueDate;
    private Integer rating;

    public Boolean changed;
    public Boolean changedStartDate;
    public Boolean changedDueDate;
    public Boolean changedRating;

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
        changedStartDate = true;

        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {

        if(this.dueDate == dueDate) return;

        changed = true;
        changedDueDate = true;

        this.dueDate = dueDate;
    }

    public Integer getRating() { return rating; }

    public void setRating(Integer rating) {

        if(this.rating == rating) return;

        changed = true;
        changedRating = true;

        this.rating = rating;
    }

    public void create(){

    }

    public void update(){

        if(!changed) return;

        if(changedStartDate){}
        if(changedDueDate){}
        if(changedRating){}
    }

    public void delete(){

    }

    public void clearChanges(){

        changed = false;
        changedStartDate = false;
        changedDueDate = false;
        changedRating = false;
    }
}
