package paf.week1.day22workshoprsvpredux.Services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paf.week1.day22workshoprsvpredux.Models.Rsvp;
import paf.week1.day22workshoprsvpredux.Repositories.RsvpRepository;

@Service
public class RsvpService {
    @Autowired
    private RsvpRepository rsvpRepo;

    public List<Rsvp> getAllRsvp() {
        return rsvpRepo.findall("rsvp", Rsvp.class);
    }

    public List<Rsvp> getRsvpByName(String name) {
        return rsvpRepo.findItems("*", "rsvp", "where full_name like '%"+name+"%'", Rsvp.class);  
    }

    public int upsertRsvp(Rsvp rsvp){
        String valuesList = "'"+rsvp.getFullName()+"','"+rsvp.getEmail()+"','"+rsvp.getPhone()+"','"+rsvp.getConfirmationDate()+"','"+rsvp.getComments()+"'";
        return rsvpRepo.upsert("rsvp", valuesList);
    }

    public int updateRsvp(Rsvp rsvp) {
        String set = "full_name='"+rsvp.getFullName()+"', phone='"+rsvp.getPhone()+"',confirmation_date='"+rsvp.getConfirmationDate()+"', comments='"+rsvp.getComments()+"'";
        return rsvpRepo.updateBy("rsvp", set, "email",rsvp.getEmail());
    }

    public int getRsvpCount() {
        int rsvpCount = 0;
        List<Integer> intList = rsvpRepo.findOneField("count(*) as count", "rsvp", Integer.class);
        rsvpCount = intList.get(0);
        // String SQL = "select count(*) as count from rsvp";
        // List<Map<String, Object>> list = rsvpRepo.genericMap(SQL);
        // rsvpCount = Integer.parseInt((String) list.get(0).get("count")) ;
        return rsvpCount;
    }

}
