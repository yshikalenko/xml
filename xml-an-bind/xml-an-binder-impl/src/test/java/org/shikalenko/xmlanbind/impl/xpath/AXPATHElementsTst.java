package org.shikalenko.xmlanbind.impl.xpath;

import static org.junit.Assert.*;
import static org.shikalenko.xmlanbind.XMLAnnotationBinder.FEATURE_XPATH;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.shikalenko.xmlanbind.annotations.XPath;
import org.shikalenko.xmlanbind.impl.TstBase;
import org.shikalenko.xmlanbind.impl.pojo.PhysicalStateObject;
import org.shikalenko.xmlanbind.impl.pojo.Sex;

public abstract class AXPATHElementsTst extends TstBase {
    
    public static class OptionObject {
        private String value;
        private String msgId;
        public String getOptionValue() {
            return value;
        }
        @XPath("value")
        public void setValue(String value) {
            this.value = value;
        }
        public String getMsgId() {
            return msgId;
        }
        @XPath("msg-id")
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

        @XPath("age")
        public void setAge(byte age) {
            assertEquals(this.age, 0);
            this.age = age;
        }
        
        @XPath("height")
        public void setHeight(short height) {
            assertEquals(this.height, 0);
            this.height = height;
        }
        
        @XPath("year-of-birth")
        public void setYearOfBirth(int yearOfBirth) {
            this.yearOfBirth = yearOfBirth;
        }

        @XPath("date-of-birth-long")
        public void setDateOfBirth(long dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        @XPath("weight-pounds")
        public void setWeightPounds(float weightPounds) {
            this.weightPounds = weightPounds;
        }
        
        @XPath("weight-grams")
        public void setWeightGrams(double weightGrams) {
            this.weightGrams = weightGrams;
        }
        @XPath("is-alien='true'")
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

    public abstract static class PersonObject {
        private String id;
        private Collection<String> names;
        private Sex sex;
        private short age;
        public String getId() {
            return id;
        }
        @XPath("id")
        public void setId(String id) {
            this.id = id;
        }
        public Collection<String> getNames() {
            return names;
        }
        protected void setNames(Collection<String> names) {
            this.names = names;
        }
        public Sex getSex() {
            return sex;
        }
        @XPath("physical/sex")
        public void setSex(Sex sex) {
            this.sex = sex;
        }
        public short getAge() {
            return age;
        }
        @XPath("physical/age")
        public void setAge(short age) {
            this.age = age;
        }
        public abstract Collection<PersonObject> getChildren();
    }
    
    public static class PersonObjectToSet extends PersonObject {
        private Collection<PersonObject> children;
        @XPath(value = "names/name", type=String.class)
        public void setNames(Collection<String> names) {
            super.setNames(names);
        }
        public Collection<PersonObject> getChildren() {
            return children;
        }
        @XPath(value="children/child", type = PersonObjectToSet.class)
        public void setChildren(Collection<PersonObject> children) {
            this.children = children;
        }
    }

    public static class PersonObjectToAdd extends PersonObject {
        private Map<String, PersonObject> children = new LinkedHashMap<>();
        
        public PersonObjectToAdd() {
            super.setNames(new LinkedList<>());
        }
        
        @XPath(value = "names/name")
        public void addName(String name) {
            super.getNames().add(name);
        }
        public Collection<PersonObject> getChildren() {
            return children.values();
        }
        @XPath(value="children/child")
        public void addChild(PersonObjectToAdd child) {
            this.children.put(child.getId(), child);
        }
    }

    public static class NamesObject {
        String firstname;
        String lastname;
        public String getFirstname() {
            return firstname;
        }
        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }
        public String getLastname() {
            return lastname;
        }
        public void setLastname(String lastname) {
            this.lastname = lastname;
        }
    }
    
    public static class TeamPersonObject {
        private String id;
        private NamesObject names;
        private Map<String, PersonObjectToAdd> children = new LinkedHashMap<>();
        private PhysicalStateObject physicalState;
        public String getId() {
            return id;
        }
        @XPath("id")
        public void setId(String id) {
            this.id = id;
        }
        public NamesObject getNames() {
            return names;
        }
        @XPath(value = "names")
        public void setNames(NamesObject names) {
            this.names = names;
        }
        public Collection<PersonObjectToAdd> getChildren() {
            return children.values();
        }
        @XPath(value="children/child")
        public void addChild(PersonObjectToAdd child) {
            this.children.put(child.getId(), child);
        }
        @XPath(value="physical")
        public void setPhysicalState(PhysicalStateObject physicalState) {
            this.physicalState = physicalState;
        }
        public PhysicalStateObject getPhysicalState() {
            return physicalState;
        }
    }
    
    
    public static class TeamObject {
        private String projectId;
        private Collection<TeamPersonObject> persons = new LinkedList<>();
        public String getProjectId() {
            return projectId;
        }
        @XPath("project-id")
        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }
        public Collection<TeamPersonObject> getPersons() {
            return persons;
        }
        @XPath("person")
        public void addPerson(TeamPersonObject person) {
            persons.add(person);
        }
        
    }
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        binder.setFeature(FEATURE_XPATH);
    }
    
    @Test
    public void testOption() throws Exception {
        String xml = "<option><value>diversification_of_investments</value><msg-id>UI.EAF.ACCOUNT_PURPOSE.EXPECTED_USE.OPTION.DIVERSIFICATION_OF_INVESTMENTS</msg-id></option>";
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
        
        String xml = "<human><age>" + age + "</age>" + 
                "<height>" + height + "</height>" + 
                "<year-of-birth>" + yearOfBirth + "</year-of-birth>" + 
                "<date-of-birth-long>" + dateOfBirthLong + "</date-of-birth-long>" + 
                "<weight-pounds>" + weightPounds + "</weight-pounds>" +
                "<weight-grams>" + weightGrams + "</weight-grams>" +
                "<is-alien>false</is-alien>" + 
                "</human>";
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
    public void testPersonToSet() throws Exception {
        String id = "3463463";
        String id1 = "3463464";
        String id11 = "3463465";
        String id12 = "3463466";
        String id2 = "3463466";
        String id21 = "3463467";
        String firstname = "Yuri";
        String firstname1 = "Nilolay";
        String firstname11 = "Barbara";
        String firstname12 = "Alice";
        String firstname2 = "Vera";
        String firstname21 = "Piter";
        String lastname = "Shikalenko";
        String lastname1 = "Shikalenko";
        String lastname11 = "Shikalenko";
        String lastname12 = "Shikalenko";
        String lastname2 = "Savostianova";
        String lastname21 = "Savostianov";
        Sex sex = Sex.male;
        Sex sex1 = Sex.male;
        Sex sex11 = Sex.female;
        Sex sex12 = Sex.female;
        Sex sex2 = Sex.female;
        Sex sex21 = Sex.male;
        short age = 62;
        short age1 = 35;
        short age11 = 3;
        short age12 = 1;
        short age2 = 37;
        short age21 = 8;
        String xml = 
                  "<person><id>" + id + "</id>\n"
                + "  <names>\n"
                + "    <name>" + firstname + "</name>\n"
                + "    <name>" + lastname + "</name>\n"
                + "  </names>\n"
                + "  <physical><sex>" + sex + "</sex><age>" + age + "</age></physical>\n"
                + "  <children>\n"
                + "     <child><id>" + id1 + "</id>\n"
                + "         <names>\n"
                + "             <name>" + firstname1 + "</name>\n"
                + "             <name>" + lastname1 + "</name>\n"
                + "          </names>\n"
                + "         <physical><sex>" + sex1 + "</sex><age>" + age1 + "</age></physical>\n"
                + "         <children>\n"
                + "             <child><id>" + id11 + "</id>\n"
                + "                 <names>\n"
                + "                     <name>" + firstname11 + "</name>\n"
                + "                     <name>" + lastname11 + "</name>\n"
                + "                 </names>\n"
                + "                 <physical><sex>" + sex11 + "</sex><age>" + age11 + "</age></physical>\n"
                + "             </child>\n"
                + "             <child><id>" + id12 + "</id>\n"
                + "                 <names>\n"
                + "                     <name>" + firstname12 + "</name>\n"
                + "                     <name>" + lastname12 + "</name>\n"
                + "                 </names>\n"
                + "                 <physical><sex>" + sex12 + "</sex><age>" + age12 + "</age></physical>\n"
                + "             </child>\n"
                + "        </children>\n"
                + "     </child>\n"
                + "     <child><id>" + id2 + "</id>\n"
                + "         <names>\n"
                + "             <name>" + firstname2 + "</name>\n"
                + "             <name>" + lastname2 + "</name>\n"
                + "          </names>\n"
                + "         <physical><sex>" + sex2 + "</sex><age>" + age2 + "</age></physical>\n"
                + "         <children>\n"
                + "             <child><id>" + id21 + "</id>\n"
                + "                 <names>\n"
                + "                     <name>" + firstname21 + "</name>\n"
                + "                     <name>" + lastname21 + "</name>\n"
                + "                 </names>\n"
                + "                 <physical><sex>" + sex21 + "</sex><age>" + age21 + "</age></physical>\n"
                + "             </child>\n"
                + "        </children>\n"
                + "     </child>\n"
                + "  </children>\n"
                + "</person>";
        xml = updateXml(xml);
        binder.cleanXPathContext();
        PersonObjectToSet object = binder.bind(PersonObjectToSet.class, xml);
        checkObject(object, xml, getPersonObjectToSetClass(), personObject -> {
            assertNotNull(personObject);
            assertEquals(id, personObject.getId());
            Collection<String> names = personObject.getNames();
            assertNotNull(names);
            assertEquals(2, names.size());
            Iterator<String> namesIterator = names.iterator();
            assertEquals(firstname, namesIterator.next());
            assertEquals(lastname, namesIterator.next());
            assertEquals(sex, personObject.getSex());
            assertEquals(age, personObject.getAge());
            
            Collection<PersonObject> children = personObject.getChildren();
            assertNotNull(children);
            assertEquals(2, children.size());
            Iterator<PersonObject> childrenIterator = children.iterator();
            PersonObject child1 = childrenIterator.next();
            assertNotNull(child1);
            assertEquals(id1, child1.getId());
            names = child1.getNames();
            assertNotNull(names);
            assertEquals(2, names.size());
            namesIterator = names.iterator();
            assertEquals(firstname1, namesIterator.next());
            assertEquals(lastname1, namesIterator.next());
            assertEquals(sex1, child1.getSex());
            assertEquals(age1, child1.getAge());
            
            Collection<PersonObject> children1 = child1.getChildren();
            assertNotNull(children1);
            assertEquals(2, children1.size());
            Iterator<PersonObject> childrenIterator1 = children1.iterator();
            PersonObject child11 = childrenIterator1.next();
            assertNotNull(child11);
            assertEquals(id11, child11.getId());
            names = child11.getNames();
            assertNotNull(names);
            assertEquals(2, names.size());
            namesIterator = names.iterator();
            assertEquals(firstname11, namesIterator.next());
            assertEquals(lastname11, namesIterator.next());
            assertEquals(sex11, child11.getSex());
            assertEquals(age11, child11.getAge());
            PersonObject child12 = childrenIterator1.next();
            assertNotNull(child12);
            assertEquals(id12, child12.getId());
            names = child12.getNames();
            assertNotNull(names);
            assertEquals(2, names.size());
            namesIterator = names.iterator();
            assertEquals(firstname12, namesIterator.next());
            assertEquals(lastname12, namesIterator.next());
            assertEquals(sex12, child12.getSex());
            assertEquals(age12, child12.getAge());

            PersonObject child2 = childrenIterator.next();
            assertNotNull(child2);
            assertEquals(id2, child2.getId());
            names = child2.getNames();
            assertNotNull(names);
            assertEquals(2, names.size());
            namesIterator = names.iterator();
            assertEquals(firstname2, namesIterator.next());
            assertEquals(lastname2, namesIterator.next());
            assertEquals(sex2, child2.getSex());
            assertEquals(age2, child2.getAge());
            
            Collection<PersonObject> children2 = child2.getChildren();
            assertNotNull(children2);
            assertEquals(1, children2.size());
            Iterator<PersonObject> childrenIterator2 = children2.iterator();
            PersonObject child21 = childrenIterator2.next();
            assertNotNull(child21);
            assertEquals(id21, child21.getId());
            names = child21.getNames();
            assertNotNull(names);
            assertEquals(2, names.size());
            namesIterator = names.iterator();
            assertEquals(firstname21, namesIterator.next());
            assertEquals(lastname21, namesIterator.next());
            assertEquals(sex21, child21.getSex());
            assertEquals(age21, child21.getAge());
        });
    }

    protected Class<? extends PersonObjectToSet> getPersonObjectToSetClass() {
        return PersonObjectToSet.class;
    }


    @Test
    public void testPersonToAdd() throws Exception {
        String id = "3463463";
        String id1 = "3463464";
        String id11 = "3463465";
        String id12 = "3463466";
        String id2 = "3463466";
        String id21 = "3463467";
        String firstname = "Yuri";
        String firstname1 = "Nilolay";
        String firstname11 = "Barbara";
        String firstname12 = "Alice";
        String firstname2 = "Vera";
        String firstname21 = "Piter";
        String lastname = "Shikalenko";
        String lastname1 = "Shikalenko";
        String lastname11 = "Shikalenko";
        String lastname12 = "Shikalenko";
        String lastname2 = "Savostianova";
        String lastname21 = "Savostianov";
        Sex sex = Sex.male;
        Sex sex1 = Sex.male;
        Sex sex11 = Sex.female;
        Sex sex12 = Sex.female;
        Sex sex2 = Sex.female;
        Sex sex21 = Sex.male;
        short age = 62;
        short age1 = 35;
        short age11 = 3;
        short age12 = 1;
        short age2 = 37;
        short age21 = 8;
        String xml = 
                  "<person><id>" + id + "</id>\n"
                + "  <names>\n"
                + "    <name>" + firstname + "</name>\n"
                + "    <name>" + lastname + "</name>\n"
                + "  </names>\n"
                + "  <physical><sex>" + sex + "</sex><age>" + age + "</age></physical>\n"
                + "  <children>\n"
                + "     <child><id>" + id1 + "</id>\n"
                + "         <names>\n"
                + "             <name>" + firstname1 + "</name>\n"
                + "             <name>" + lastname1 + "</name>\n"
                + "          </names>\n"
                + "         <physical><sex>" + sex1 + "</sex><age>" + age1 + "</age></physical>\n"
                + "         <children>\n"
                + "             <child><id>" + id11 + "</id>\n"
                + "                 <names>\n"
                + "                     <name>" + firstname11 + "</name>\n"
                + "                     <name>" + lastname11 + "</name>\n"
                + "                 </names>\n"
                + "                 <physical><sex>" + sex11 + "</sex><age>" + age11 + "</age></physical>\n"
                + "             </child>\n"
                + "             <child><id>" + id12 + "</id>\n"
                + "                 <names>\n"
                + "                     <name>" + firstname12 + "</name>\n"
                + "                     <name>" + lastname12 + "</name>\n"
                + "                 </names>\n"
                + "                 <physical><sex>" + sex12 + "</sex><age>" + age12 + "</age></physical>\n"
                + "             </child>\n"
                + "        </children>\n"
                + "     </child>\n"
                + "     <child><id>" + id2 + "</id>\n"
                + "         <names>\n"
                + "             <name>" + firstname2 + "</name>\n"
                + "             <name>" + lastname2 + "</name>\n"
                + "          </names>\n"
                + "         <physical><sex>" + sex2 + "</sex><age>" + age2 + "</age></physical>\n"
                + "         <children>\n"
                + "             <child><id>" + id21 + "</id>\n"
                + "                 <names>\n"
                + "                     <name>" + firstname21 + "</name>\n"
                + "                     <name>" + lastname21 + "</name>\n"
                + "                 </names>\n"
                + "                 <physical><sex>" + sex21 + "</sex><age>" + age21 + "</age></physical>\n"
                + "             </child>\n"
                + "        </children>\n"
                + "     </child>\n"
                + "  </children>\n"
                + "</person>";
        xml = updateXml(xml);        
        binder.cleanXPathContext();
        PersonObjectToAdd object = binder.bind(PersonObjectToAdd.class, xml);
        checkObject(object, xml, getPersonObjectToAddClass(), personObject -> {
            assertNotNull(personObject);
            assertEquals(id, personObject.getId());
            Collection<String> names = personObject.getNames();
            assertNotNull(names);
            assertEquals(2, names.size());
            Iterator<String> namesIterator = names.iterator();
            assertEquals(firstname, namesIterator.next());
            assertEquals(lastname, namesIterator.next());
            assertEquals(sex, personObject.getSex());
            assertEquals(age, personObject.getAge());
            
            Collection<PersonObject> children = personObject.getChildren();
            assertNotNull(children);
            assertEquals(2, children.size());
            Iterator<PersonObject> childrenIterator = children.iterator();
            PersonObject child1 = childrenIterator.next();
            assertNotNull(child1);
            assertEquals(id1, child1.getId());
            names = child1.getNames();
            assertNotNull(names);
            assertEquals(2, names.size());
            namesIterator = names.iterator();
            assertEquals(firstname1, namesIterator.next());
            assertEquals(lastname1, namesIterator.next());
            assertEquals(sex1, child1.getSex());
            assertEquals(age1, child1.getAge());
            
            Collection<PersonObject> children1 = child1.getChildren();
            assertNotNull(children1);
            assertEquals(2, children1.size());
            Iterator<PersonObject> childrenIterator1 = children1.iterator();
            PersonObject child11 = childrenIterator1.next();
            assertNotNull(child11);
            assertEquals(id11, child11.getId());
            names = child11.getNames();
            assertNotNull(names);
            assertEquals(2, names.size());
            namesIterator = names.iterator();
            assertEquals(firstname11, namesIterator.next());
            assertEquals(lastname11, namesIterator.next());
            assertEquals(sex11, child11.getSex());
            assertEquals(age11, child11.getAge());
            PersonObject child12 = childrenIterator1.next();
            assertNotNull(child12);
            assertEquals(id12, child12.getId());
            names = child12.getNames();
            assertNotNull(names);
            assertEquals(2, names.size());
            namesIterator = names.iterator();
            assertEquals(firstname12, namesIterator.next());
            assertEquals(lastname12, namesIterator.next());
            assertEquals(sex12, child12.getSex());
            assertEquals(age12, child12.getAge());

            PersonObject child2 = childrenIterator.next();
            assertNotNull(child2);
            assertEquals(id2, child2.getId());
            names = child2.getNames();
            assertNotNull(names);
            assertEquals(2, names.size());
            namesIterator = names.iterator();
            assertEquals(firstname2, namesIterator.next());
            assertEquals(lastname2, namesIterator.next());
            assertEquals(sex2, child2.getSex());
            assertEquals(age2, child2.getAge());
            
            Collection<PersonObject> children2 = child2.getChildren();
            assertNotNull(children2);
            assertEquals(1, children2.size());
            Iterator<PersonObject> childrenIterator2 = children2.iterator();
            PersonObject child21 = childrenIterator2.next();
            assertNotNull(child21);
            assertEquals(id21, child21.getId());
            names = child21.getNames();
            assertNotNull(names);
            assertEquals(2, names.size());
            namesIterator = names.iterator();
            assertEquals(firstname21, namesIterator.next());
            assertEquals(lastname21, namesIterator.next());
            assertEquals(sex21, child21.getSex());
            assertEquals(age21, child21.getAge());
        });
    }
    

    protected Class<? extends PersonObjectToAdd> getPersonObjectToAddClass() {
        return PersonObjectToAdd.class;
    }


    @Test
    public void testTeam() throws Exception {
        String xml = "<team><project-id>xml-an-bind</project-id>";
        xml += "<person><id>3463463</id><names><firstname>Yuri</firstname><lastname>Shikalenko</lastname></names><physical><sex>male</sex><age>62</age></physical></person>";
        xml += "<person><id>3456454</id><names><firstname>Eliza</firstname><lastname>Bowl</lastname></names><physical><sex>female</sex><age>35</age></physical></person>";
        xml += "<person><id>3456465</id><names><firstname>John</firstname><lastname>Robbin</lastname></names><physical><sex>male</sex><age>28</age></physical></person>";
        xml += "</team>";        
        xml = updateXml(xml);
        binder.cleanXPathContext();
        TeamObject object = binder.bind(TeamObject.class, xml);
        checkObject(object, xml, getTeamObjectClass(), teamObject -> {
            assertNotNull(teamObject);
            assertEquals("xml-an-bind", teamObject.getProjectId());
            Collection<TeamPersonObject> persons = teamObject.getPersons();
            assertEquals(3, persons.size());
            Iterator<TeamPersonObject> iterator = persons.iterator();
            {
                TeamPersonObject personObject = iterator.next();
                assertEquals("3463463", personObject.getId());
                NamesObject names = personObject.getNames();
                assertNotNull(names);
                assertEquals("Yuri", names.getFirstname());
                assertEquals("Shikalenko", names.getLastname());
                PhysicalStateObject physicalState = personObject.getPhysicalState();
                assertNotNull(physicalState);
                assertEquals(Sex.male, physicalState.getSex());
                assertEquals(62, physicalState.getAge());
            }
            {
                TeamPersonObject personObject = iterator.next();
                assertEquals("3456454", personObject.getId());
                NamesObject names = personObject.getNames();
                assertNotNull(names);
                assertEquals("Eliza", names.getFirstname());
                assertEquals("Bowl", names.getLastname());
                PhysicalStateObject physicalState = personObject.getPhysicalState();
                assertNotNull(physicalState);
                assertEquals(Sex.female, physicalState.getSex());
                assertEquals(35, physicalState.getAge());
            }
            {
                TeamPersonObject personObject = iterator.next();
                assertEquals("3456465", personObject.getId());
                NamesObject names = personObject.getNames();
                assertNotNull(names);
                assertEquals("John", names.getFirstname());
                assertEquals("Robbin", names.getLastname());
                PhysicalStateObject physicalState = personObject.getPhysicalState();
                assertNotNull(physicalState);
                assertEquals(Sex.male, physicalState.getSex());
                assertEquals(28, physicalState.getAge());
            }
        });
    }


    protected Class<? extends TeamObject> getTeamObjectClass() {
        return TeamObject.class;
    }
    
    
}
