package org.shikalenko.xmlanbind.impl.xpath;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.shikalenko.xmlanbind.annotations.XPath;
import org.shikalenko.xmlanbind.impl.TstBase;
import org.shikalenko.xmlanbind.impl.pojo.Sex;
import static org.shikalenko.xmlanbind.XMLAnnotationBinder.FEATURE_XPATH;

public abstract class AXPATHAttributesTst extends TstBase {

    public static class OptionObject {
        private String value;
        private String msgId;
        public String getOptionValue() {
            return value;
        }
        @XPath("@value")
        public void setValue(String value) {
            this.value = value;
        }
        public String getMsgId() {
            return msgId;
        }
        @XPath("@msg-id")
        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }
        
    }
    
    public static class HumanObject {
        
        private int age;
        private short height;
        private int yearOfBirth;
        private long dateOfBirth;
        private float weightPounds;
        private double weightGrams;
        private boolean alien;

        public int getAge() {
            return age;
        }

        @XPath("@age")
        public void setAge(byte age) {
            assertEquals(this.age, 0);
            this.age = age;
        }
        
        @XPath("@height")
        public void setHeight(short height) {
            assertEquals(this.height, 0);
            this.height = height;
        }
        
        @XPath("@year-of-birth")
        public void setYearOfBirth(int yearOfBirth) {
            this.yearOfBirth = yearOfBirth;
        }

        @XPath("@date-of-birth-long")
        public void setDateOfBirth(long dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        @XPath("@weight-pounds")
        public void setWeightPounds(float weightPounds) {
            this.weightPounds = weightPounds;
        }
        
        @XPath("@weight-grams")
        public void setWeightGrams(double weightGrams) {
            this.weightGrams = weightGrams;
        }
        @XPath("@is-alien='true'")
        public void setAlien(boolean alien) {
            this.alien = alien;
        }

        public short getHeight() {
            return height;
        }

        public int getYearOfBirth() {
            return yearOfBirth;
        }

        public long getDateOfBirth() {
            return dateOfBirth;
        }

        public float getWeightPounds() {
            return weightPounds;
        }

        public double getWeightGrams() {
            return weightGrams;
        }

        public boolean isAlien() {
            return alien;
        }
        
    }
    
    public static class TeamObject {
        private String projectId;
        private Collection<PersonObject> persons = new LinkedList<>();
        public String getProjectId() {
            return projectId;
        }
        @XPath("@project-id")
        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }
        public Collection<PersonObject> getPersons() {
            return persons;
        }
        @XPath("person")
        public void addPerson(PersonObject person) {
            persons.add(person);
        }
    }

    public static class PersonObject {
        private String id;
        private String firstname;
        private String lastname;
        private Sex sex;
        private short age;
        public String getId() {
            return id;
        }
        @XPath("@id")
        public void setId(String id) {
            this.id = id;
        }
        public String getFirstname() {
            return firstname;
        }
        @XPath("names/@firstname")
        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }
        public String getLastname() {
            return lastname;
        }
        @XPath("names/@lastname")
        public void setLastname(String lastname) {
            this.lastname = lastname;
        }
        public Sex getSex() {
            return sex;
        }
        @XPath("physical/@sex")
        public void setSex(Sex sex) {
            this.sex = sex;
        }
        public short getAge() {
            return age;
        }
        @XPath("physical/@age")
        public void setAge(short age) {
            this.age = age;
        }    
        
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        binder.setFeature(FEATURE_XPATH);
    }

    @Test
    public void testOption() throws Exception {
        String xml = "<option value='diversification_of_investments' msg-id='UI.EAF.ACCOUNT_PURPOSE.EXPECTED_USE.OPTION.DIVERSIFICATION_OF_INVESTMENTS'/>";
        xml = updateXml(xml);
        binder.cleanXPathContext();
        OptionObject object = binder.bind(OptionObject.class, xml);
        checkObject(object, xml, getOptionObjectClass(), optionObject -> {
            assertNotNull(optionObject);
            assertEquals("diversification_of_investments", optionObject.getOptionValue());
            assertEquals("UI.EAF.ACCOUNT_PURPOSE.EXPECTED_USE.OPTION.DIVERSIFICATION_OF_INVESTMENTS", optionObject.getMsgId());
        });
    }

    protected Class<? extends OptionObject> getOptionObjectClass() {
        return OptionObject.class;
    }
    
    @Test
    public void testHuman() throws Exception {
        byte age = 62;
        short height = 179;
        int yearOfBirth = 1959;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, yearOfBirth);
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date dateOfBirth = calendar.getTime();
        long dateOfBirthLong = dateOfBirth.getTime();
        float weightKg = 69.5f;
        float weightPounds = weightKg / 0.453592f;
        double weightGrams = ((double)weightKg) * 1000d;
        
        String xml = "<human age='" + age + 
                "' height='" + height + 
                "' year-of-birth='" + yearOfBirth + 
                "' date-of-birth-long='" + dateOfBirthLong + 
                "' weight-pounds='" + weightPounds +
                "' weight-grams='" + weightGrams +
                "' is-alien='false'" + 
                "/>";
        xml = updateXml(xml);
        binder.cleanXPathContext();
        HumanObject object = binder.bind(HumanObject.class, xml);
        checkObject(object, xml, getHumanObjectClass(), humanObject -> {
            assertNotNull(humanObject);
            assertEquals(age, humanObject.getAge());
            assertEquals(height, humanObject.getHeight());
            assertEquals(yearOfBirth, humanObject.getYearOfBirth());
            assertEquals(dateOfBirthLong, humanObject.getDateOfBirth());
            assertEquals(weightPounds, humanObject.getWeightPounds(), 0);
            assertEquals(weightGrams, humanObject.getWeightGrams(), 0);
            assertFalse(humanObject.isAlien());
        });
    }

    protected Class<? extends HumanObject> getHumanObjectClass() {
        return HumanObject.class;
    }
    
    @Test
    public void testTeam() throws Exception {
        String xml = "<team project-id='xml-an-bind'>";
        xml += "<person id='3463463'><names firstname='Yuri' lastname='Shikalenko'/><physical sex='male' age='62'/></person>";
        xml += "<person id='3456454'><names firstname='Eliza' lastname='Bowl'/><physical sex='female' age='35'/></person>";
        xml += "<person id='3456465'><names firstname='John' lastname='Robbin'/><physical sex='male' age='28'/></person>";
        xml += "</team>";
        xml = updateXml(xml);
        binder.cleanXPathContext();
        TeamObject object = binder.bind(TeamObject.class, xml);
        checkObject(object, xml, getTeamObjectClass(), teamObject -> {
            assertNotNull(teamObject);
            assertEquals("xml-an-bind", teamObject.getProjectId());
            Collection<PersonObject> persons = teamObject.getPersons();
            assertEquals(3, persons.size());
            Iterator<PersonObject> iterator = persons.iterator();
            {
                PersonObject personObject = iterator.next();
                assertEquals("3463463", personObject.getId());
                assertEquals("Yuri", personObject.getFirstname());
                assertEquals("Shikalenko", personObject.getLastname());
                assertEquals(Sex.male, personObject.getSex());
                assertEquals(62, personObject.getAge());
            }
            {
                PersonObject personObject = iterator.next();
                assertEquals("3456454", personObject.getId());
                assertEquals("Eliza", personObject.getFirstname());
                assertEquals("Bowl", personObject.getLastname());
                assertEquals(Sex.female, personObject.getSex());
                assertEquals(35, personObject.getAge());
            }
            {
                PersonObject personObject = iterator.next();
                assertEquals("3456465", personObject.getId());
                assertEquals("John", personObject.getFirstname());
                assertEquals("Robbin", personObject.getLastname());
                assertEquals(Sex.male, personObject.getSex());
                assertEquals(28, personObject.getAge());
            }
        });
    }

    protected Class<? extends TeamObject> getTeamObjectClass() {
        return TeamObject.class;
    }
    
    
}
