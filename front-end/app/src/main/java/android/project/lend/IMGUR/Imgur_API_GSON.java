package android.project.lend.IMGUR;

public class Imgur_API_GSON
{
    private Imgur_Data_API_GSON data;

    private String success;

    private String status;

    public Imgur_Data_API_GSON getData ()
    {
        return data;
    }

    public void setData (Imgur_Data_API_GSON data)
    {
        this.data = data;
    }

    public String getSuccess ()
    {
        return success;
    }

    public void setSuccess (String success)
    {
        this.success = success;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "[data = "+data+", success = "+success+", status = "+status+"]";
    }
}
			
			