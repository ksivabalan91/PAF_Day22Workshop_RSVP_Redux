package paf.week1.day22workshoprsvpredux.Models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rsvp {
    private String fullName=null;
    private String email=null;
    private String phone=null;
    private Date confirmationDate=null;
    private String comments=null;    
}
