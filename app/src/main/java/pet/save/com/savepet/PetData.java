package pet.save.com.savepet;

/**
 * Created by user on 2018/3/7.
 */

public class PetData {

    private   String petName;
    private String date;
    //照片路徑變為字串
    public String uri;
    public PetData(){

    }

    public PetData(String petName, String date,String uri){
        this.petName = petName;
        this.date = date;
        this.uri=uri;
    }
    public String getPetName(){
        return petName;
    }
    public void setPetName(String petName){
        this.petName = petName;
    }
    public String getdate(){
        return date;
    }
    public void setdate(String date){
        this.date = date;
    }


}
