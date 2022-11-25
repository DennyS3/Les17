import java.util.ArrayList;
import java.util.List;

public class Document {


    private String phone = "not phone";
    private  String email = "not email";
    private String docNum = "not docNum";

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    @Override
    public String toString() {
        return "Document{" +
                "phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", docNum='" + docNum + '\'' +
                '}';
    }
}
