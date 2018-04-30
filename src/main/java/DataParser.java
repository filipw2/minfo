import data.dao.*;
import data.model.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;


public class DataParser {
    SessionDAO baseDAO = new SessionDAO();
    EventDAO eventDAO = new EventDAO();
    RiderDAO riderDAO = new RiderDAO();
    public DataParser() {

       // parseEvents();
        // parseResults();
        parseSessions();
    }


    public void parseSessions() {

        List<Event> eventList =eventDAO.getEvents();// eventDAO.getEventsForKey(new EventID("SPA",2012));
        eventList.stream().forEach(d -> {

            try {
                // FOR EACH EVENT
                Elements categories = Jsoup.connect("http://www.motogp.com/en/Results+Statistics/" + d.getEventID().getYear() + "/" + d.getEventID().getSymbol()).header("Accept-Encoding", "gzip, deflate")
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                        .maxBodySize(0)
                        .timeout(600000)
                        .get()
                        .select("#category>option");
                System.out.println(d.getEventID().getYear()+ "/" + d.getEventID().getSymbol());

                Set<Bike> bikes = new HashSet<>();
                Set<Team> teams = new HashSet<>();
                Set<SessionType> stype = new HashSet<>();
                for (Element category : categories) {
                    List<Session> sessions = new ArrayList<>();
                    List<Results> resultsList = new ArrayList<>();

                    String cat = category.attr("value").toString();

                    // FOR EACH CATEGORY
                    Document document = Jsoup.connect("http://www.motogp.com/en/Results+Statistics/" + d.getEventID().getYear() + "/" + d.getEventID().getSymbol() + "/" + cat).header("Accept-Encoding", "gzip, deflate")
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                            .maxBodySize(0)
                            .timeout(600000)
                            .get();
                    for (Element e : document.select("#session > option")) {
                        Session session = new Session();
                        session.setEvent(d);
                        String t= e.attr("value").toString();
                        Optional<SessionType> optionalt=stype.stream().filter(bi -> bi.getTname().equals(t)).findFirst();
                        SessionType type;
                        if(optionalt.isPresent())
                            type=optionalt.get();
                        else {
                            type=new SessionType(t);
                            stype.add(type);
                        }
                        session.setTname(type);
                        session.setCategory(cat);
                        //System.out.println(session.getTname().getTname());
                        sessions.add(session);

                        // FOR EACH SESSION
                        Document doc = Jsoup.connect("http://www.motogp.com/en/Results+Statistics/" + d.getEventID().getYear() + "/" + d.getEventID().getSymbol() + "/" + cat+"/"+session.getTname().getTname()).header("Accept-Encoding", "gzip, deflate")
                                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                                .maxBodySize(0)
                                .timeout(600000)
                                .get();
                        Elements elements = doc.select("table.width100:nth-child(5) > tbody:nth-child(2) > tr");
                        elements.stream().forEach(element -> {
                            Results results = getResults(bikes, teams, session, element);
                            if (results == null) return;
                            resultsList.add(results);
                        });
                    }
                    baseDAO.insertMultiple(sessions);
                    baseDAO.insertMultiple(resultsList);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
            System.out.println("FINISH");
    }

    private Results getResults(Set<Bike> bikes, Set<Team> teams, Session session, Element element) {
        Results results = new Results();
        int selector=0;
        if(session.getTname().getTname().equals("RAC"))
            selector=1;
        String regex = "\\d+";
        if(!element.select("td:nth-child(1)").text().matches(regex)) return null;
        String pos= element.select("td:nth-child(1)").text();
        int s=0;
        if(!pos.isEmpty())
            s = Integer.valueOf(pos);

        results.setPosition(s);
        results.setSession(session);

        String team= element.select("td:nth-child("+(5+selector)+")").text();
        Optional<Team> optionalt=teams.stream().filter(bi -> bi.getTeamname().equals(team)).findFirst();
        Team team1;
        if(optionalt.isPresent())
            team1=optionalt.get();
        else {
            team1=new Team(team);
            teams.add(team1);
        }
        String b= element.select("td:nth-child("+(6+selector)+")").text();
        Optional<Bike> optional=bikes.stream().filter(bi -> bi.getBike().equals(b)).findFirst();
        Bike bike;
        if(optional.isPresent())
            bike=optional.get();
        else {
            bike=new Bike(b);
            bikes.add(bike);
        }
        results.setTime(element.select("td:nth-child("+(8+selector)+")").text());
        results.setTeam(team1);
        results.setBike(bike);
        results.setRider(parseRider(element,selector));
        return results;
    }

    private Rider parseRider(Element element,int s) {
        String number = element.select("td:nth-child("+(2+s)+")").text();

        int n=0;
        try {
        if(!element.select("td:nth-child("+(2+s)+")").text().isEmpty())
               n = Integer.valueOf(number);
        }catch (NumberFormatException e){}
        String nation = element.select("td:nth-child("+(4+s)+")").text();
        String[] rider = element.select("td:nth-child("+(3+s)+")>a").text().split(" ");
        String name = rider[0];
        String surname = "";
        for (int i = 1; i < rider.length; i++) {
            surname = rider[i] + " ";
        }return riderDAO.getRiderIDifNotExistsInsert(new Rider(
            name, surname.trim(), n, nation
    ));

    }

    public void parseResults() {
        SessionDAO baseDAO = new SessionDAO();

        EventDAO eventDAO = new EventDAO();

        List<Event> eventList = new ArrayList<>();//eventDAO.getEvents();
        List<Results> results1 = new ArrayList<>();

        eventList.add(new Event("QAT", "Grand Prix of Qatar", new RaceTrack("Losail International Circuit"), 2018));
        eventList.stream().forEach(d -> {

            try {
                Document doc = Jsoup.connect("http://www.motogp.com/en/Results+Statistics/" + d.getEventID().getYear() + "/" + d.getEventID().getSymbol() + "/MotoGP").get();
                Results results = new Results();
                Elements elements = doc.select("table.width100:nth-child(5) > tbody:nth-child(2) > tr");
                Session session = new Session();
                session.setEvent(d);
                session.setTname(new SessionType());
                elements.stream().forEach(e -> {
                    System.out.println(e.toString());
                    results.setPosition(Integer.valueOf(e.select("td:nth-child(1)").text()));
                    //results.setSession();
                    int s = Integer.valueOf(e.select("td:nth-child(1)").text());
                    System.out.println(s);
                });

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {

            }

        });


//        .stream().forEach(d->{
//            System.out.println(d.getEventID().getSymbol()+" "+d.getTrack().getName());
//        });
        Session session = baseDAO.getSession("Q2", "NED", 2016);
        System.out.println(session);
//        Session session = new Session(new SessionType("Q2"),baseDAO.insert(new Event("NED","Motul TT Assen",new RaceTrack("TT Circuit Assen"),2016)));
//        Rider rider = new Rider("Marc", "Marquez", 93, "SPA", new Team("Repsol Honda Team"), new Bike("Honda"));
//        rider.setId(13);
//       // baseDAO.insert(rider);
//        baseDAO.insert(new Results(session,rider,4));
        //new BaseDAO().insert(new Results(session,rider,4));
    }


    public void parseEvents() {
        Document doc;
        BaseDAO baseDAO = new BaseDAO();
        try {
            for (int i = 2014; i <2019 ; i++) {
            doc = Jsoup.connect("http://www.motogp.com/en/Results+Statistics/" + i).get();
            baseDAO.insertMultiple(parseYear(doc, i));
             }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Event> parseYear(Document doc, int i) {
        Elements elements = doc.select("#event > option");
        List<Event> list = new ArrayList<>();
        for (Element element : elements) {
            Event evnt = new Event();
            evnt.setEventID(new EventID(element.attr("value").toString(), i));
            String title = element.attr("title");
            String[] split = title.split(" - ");
            evnt.setTitle(split[0]);
            evnt.setTrack(new RaceTrack(split[1]));
            list.add(evnt);
            System.out.println(evnt.getTitle() + " " + evnt.getTrack());
        }
        return list;
    }

}
