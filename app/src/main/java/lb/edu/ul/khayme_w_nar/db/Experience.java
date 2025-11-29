package lb.edu.ul.khayme_w_nar.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "experience_table")
public class Experience {
    @PrimaryKey(autoGenerate = true)
    private int id;

    // fullName is the name of the user that published the experience
    private String fullName;
    private String phoneNumber;
    private String paragraph;

    public Experience(String fullName, String phoneNumber, String paragraph) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.paragraph = paragraph;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }
}
