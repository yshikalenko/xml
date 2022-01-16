<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Что такое *xml-an-bind*?](#%D1%87%D1%82%D0%BE-%D1%82%D0%B0%D0%BA%D0%BE%D0%B5-xml-an-bind)
  - [Проблема](#%D0%BF%D1%80%D0%BE%D0%B1%D0%BB%D0%B5%D0%BC%D0%B0)
  - [Решение](#%D1%80%D0%B5%D1%88%D0%B5%D0%BD%D0%B8%D0%B5)
    - [Абстрактный уровень](#%D0%B0%D0%B1%D1%81%D1%82%D1%80%D0%B0%D0%BA%D1%82%D0%BD%D1%8B%D0%B9-%D1%83%D1%80%D0%BE%D0%B2%D0%B5%D0%BD%D1%8C)
      - [Пол персоны](#%D0%BF%D0%BE%D0%BB-%D0%BF%D0%B5%D1%80%D1%81%D0%BE%D0%BD%D1%8B)
      - [Персона](#%D0%BF%D0%B5%D1%80%D1%81%D0%BE%D0%BD%D0%B0)
      - [Team](#team)
      - [Абстрактная фабрика](#%D0%B0%D0%B1%D1%81%D1%82%D1%80%D0%B0%D0%BA%D1%82%D0%BD%D0%B0%D1%8F-%D1%84%D0%B0%D0%B1%D1%80%D0%B8%D0%BA%D0%B0)
    - [Нижний уровень. Реализация.](#%D0%BD%D0%B8%D0%B6%D0%BD%D0%B8%D0%B9-%D1%83%D1%80%D0%BE%D0%B2%D0%B5%D0%BD%D1%8C-%D1%80%D0%B5%D0%B0%D0%BB%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D1%8F)
      - [Имена персоны](#%D0%B8%D0%BC%D0%B5%D0%BD%D0%B0-%D0%BF%D0%B5%D1%80%D1%81%D0%BE%D0%BD%D1%8B)
      - [Физические характеристики персоны](#%D1%84%D0%B8%D0%B7%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B8%D0%B5-%D1%85%D0%B0%D1%80%D0%B0%D0%BA%D1%82%D0%B5%D1%80%D0%B8%D1%81%D1%82%D0%B8%D0%BA%D0%B8-%D0%BF%D0%B5%D1%80%D1%81%D0%BE%D0%BD%D1%8B)
      - [Персона](#%D0%BF%D0%B5%D1%80%D1%81%D0%BE%D0%BD%D0%B0-1)
      - [Team](#team-1)
      - [Фабрика Team](#%D1%84%D0%B0%D0%B1%D1%80%D0%B8%D0%BA%D0%B0-team)
      - [Реализация абстрактной фабрики](#%D1%80%D0%B5%D0%B0%D0%BB%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D1%8F-%D0%B0%D0%B1%D1%81%D1%82%D1%80%D0%B0%D0%BA%D1%82%D0%BD%D0%BE%D0%B9-%D1%84%D0%B0%D0%B1%D1%80%D0%B8%D0%BA%D0%B8)
    - [Юнит тест фабрики](#%D1%8E%D0%BD%D0%B8%D1%82-%D1%82%D0%B5%D1%81%D1%82-%D1%84%D0%B0%D0%B1%D1%80%D0%B8%D0%BA%D0%B8)
      - [Реализация фабрики Team](#%D1%80%D0%B5%D0%B0%D0%BB%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D1%8F-%D1%84%D0%B0%D0%B1%D1%80%D0%B8%D0%BA%D0%B8-team)
  - [Получение данных из атрибутов](#%D0%BF%D0%BE%D0%BB%D1%83%D1%87%D0%B5%D0%BD%D0%B8%D0%B5-%D0%B4%D0%B0%D0%BD%D0%BD%D1%8B%D1%85-%D0%B8%D0%B7-%D0%B0%D1%82%D1%80%D0%B8%D0%B1%D1%83%D1%82%D0%BE%D0%B2)
  - [Использование выражений *XPath*](#%D0%B8%D1%81%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5-%D0%B2%D1%8B%D1%80%D0%B0%D0%B6%D0%B5%D0%BD%D0%B8%D0%B9-xpath)
  - [Обработка XML с Namespace](#%D0%BE%D0%B1%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%BA%D0%B0-xml-%D1%81-namespace)
  - [Правила умалчивания:](#%D0%BF%D1%80%D0%B0%D0%B2%D0%B8%D0%BB%D0%B0-%D1%83%D0%BC%D0%B0%D0%BB%D1%87%D0%B8%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

### Что такое *xml-an-bind*?

***xml-an-bind*** - это java библиотека, которая позволяет с помощью annotations решать следующую проблему.

#### Проблема

Допустим у нас есть xml файл

```xml
<team>
	<project-id>xml-an-bind</project-id>
	<person>
		<id>3463463</id>
		<names>
			<firstname>John</firstname>
			<lastname>Plenkovich</lastname>
		</names>
		<physical>
			<sex>male</sex>
			<age>42</age>
		</physical>
	</person>
	<person>
		<id>3456454</id>
		<names>
			<firstname>Eliza</firstname>
			<lastname>Bowl</lastname>
		</names>
		<physical>
			<sex>female</sex>
			<age>35</age>
		</physical>
	</person>
	<person>
		<id>3456465</id>
		<names>
			<firstname>John</firstname>
			<lastname>Robbin</lastname>
		</names>
		<physical>
			<sex>male</sex>
			<age>28</age>
		</physical>
	</person>
</team>
```

Задача: прочитать данные ***team*** в java объект.

Бесчисленное число раз приходилось программистам решать подобную задачу.

Бесчисленное количество решений.

Скорее всего решений столько же, сколько раз подобная задача встречалась.

Проблемой является найти общий шаблон для такого решения.

Библиотека ***xml-an-bind*** предлагает один из таких шаблонов.

#### Решение

Лучше всего в решении идти от использования. Надо запроектировать сущности, такие, которые будет использовать код верхнего уровня. 

В приведенном XML выделяются четыре сущности:

1. Team. Явно должна использоваться верхним уровнем.
2. Person. Скорее всего должна использоваться верхним уровнем, так как Team содержит несколько Person.
3. Names. Скорее не должна использоваться верхним уровням, так как доступ к этим данным может делегироваться Person.
4. Physical. Скорее не должна использоваться верхним уровням, так как доступ к этим данным может делегироваться Person.

Поэтому для этих сущностей надо запроектировать интерфейсы или абстрактный уровень, удобный для кода верхнего уровня.

##### Абстрактный уровень

###### Пол персоны

```java
package org.mydomain.example.abs;

public enum Sex {
    male,
    female
}
```

###### Персона

```java
package org.mydomain.example.abs;

public interface Person {

    String getId();

    String getFirstname();
    
    String getLastname();
    
    Sex getSex();
    
    int getAge();
    
}
```

###### Team

```java
package org.mydomain.example.abs;

import java.util.Collection;


public interface Team {

    String getProjectId();

    Collection<Person> getPersons();

}
```

Также нам нужна фабрика, которая будет создавать инстанс Team и заполнять его из xml

Пусть у нас будет абстрактная фабрика, и ее реализация, которая будет как то jnjected в приложение, способов может быть много, здесь касаться их не будем.

###### Абстрактная фабрика

```java
package org.mydomain.example.abs;

import org.w3c.dom.Node;

public interface AbstractEntitiesFactory {
    Team newTeam(Node node) throws FactoryException;
}
```

##### Нижний уровень. Реализация.

###### Имена персоны

```java
package org.mydomain.example.impl;

public class NamesImpl {
    private String firstname;
    private String lastname;
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
```

######  Физические характеристики персоны

```java
package org.mydomain.example.impl;

import org.mydomain.example.abs.Sex;

public class PhysicalStateImpl {
    private Sex sex;
    private int age;
    public Sex getSex() {
        return sex;
    }
    public void setSex(Sex sex) {
        this.sex = sex;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
```

######  Персона

```java
package org.mydomain.example.impl;

import org.mydomain.example.abs.Person;
import org.mydomain.example.abs.Sex;
import org.shikalenko.xmlanbind.annotations.Element;

public class PersonImpl implements Person {
    private String id;
    private NamesImpl names = new NamesImpl();
    private PhysicalStateImpl physicalState = new PhysicalStateImpl();
    @Override
    public String getId() {
        return id;
    }
    @Override
    public String getFirstname() {
        return names.getFirstname();
    }
    @Override
    public String getLastname() {
        return names.getLastname();
    }
    @Override
    public Sex getSex() {
        return physicalState.getSex();
    }
    @Override
    public int getAge() {
        return physicalState.getAge();
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setNames(NamesImpl names) {
        this.names = names;
    }
    @Element("physical")
    public void setPhysicalState(PhysicalStateImpl physicalState) {
        this.physicalState = physicalState;
    }
}
```

Аннотация *@Element* нужна тогда , когда по имени метода не определяется однозначно элемента или атрибута, значение которого надо передать в метод.

Аннотация *@Element("physical")* нам понадобилась, потому что "*physical*" имя элемента не соответствует имени метода *setPhysicalState*.

При этом другие "set" методы в классах *PersonImpl, PhysicalStateImpl, NamesImpl* не содержат аннотаций. Потому что на них распространяются [Правила умалчивания:](#%D0%BF%D1%80%D0%B0%D0%B2%D0%B8%D0%BB%D0%B0-%D1%83%D0%BC%D0%B0%D0%BB%D1%87%D0%B8%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F):

###### Team

```java
package org.mydomain.example.impl;

import java.util.Collection;
import java.util.LinkedList;

import org.mydomain.example.abs.Person;
import org.mydomain.example.abs.Team;
import org.shikalenko.xmlanbind.annotations.Element;

public class TeamImpl implements Team {
    private String projectId;
    private Collection<Person> persons = new LinkedList<>();
    @Override
    public String getProjectId() {
        return projectId;
    }
    @Element("project-id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    @Override
    public Collection<Person> getPersons() {
        return persons;
    }
    @Element("person")    
    public void addPerson(PersonImpl person) {
        persons.add(person);
    }    
}
```

Аннотация *@Element* здесь использована чтобы указать значение какого элемента надо использовать для передачи в методы *setProjectId* и *addPerson*.

Аннотация *@Element("project-id")* нам понадобилась, потому что "*project-id*" имя элемента не соответствует имени метода *getProjectId*. И мы бы не смогли в java объявить метод *getProject-id*.

Аннотация *@Element("person")* нам понадобилась, потому что имя метода *addPerson* не начинается с префикса *"get"*. Мы бы, конечно, смогли в java объявить метод *setPerson*, который бы несколько раз добавлял персону, но тогда мы бы нарушили соглашения java и ввели бы в заблуждения тех, кто будет использовать этот код.

###### Фабрика Team

```java
package org.mydomain.example.impl;

import org.mydomain.example.abs.Team;
import org.w3c.dom.Node;

public class TeamFactoryImpl {

    public Team newTeam(Node node) {
        return new TeamImpl(); //Let it be dummy until unit test has been developed
    }
    
}
```

###### Реализация абстрактной фабрики

```java
package org.mydomain.example.impl;

import org.mydomain.example.abs.AbstractEntitiesFactory;
import org.mydomain.example.abs.FactoryException;
import org.mydomain.example.abs.Team;
import org.w3c.dom.Node;

public class EntitiesFactoryImpl implements AbstractEntitiesFactory {

    private TeamFactoryImpl teamFactory = new TeamFactoryImpl();
    
    @Override
    public Team newTeam(Node node) throws FactoryException {
        return teamFactory.newTeam(node);
    }

}
```

##### Юнит тест фабрики

Лучшая практика - начинать разработку с юнит теста. Да мы уже написали несколько классов реализации, но это простые POJO, а вот заполнение их из XML надо проверить.

```java
package org.mydomain.example.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.mydomain.example.abs.Person;
import org.mydomain.example.abs.Sex;
import org.mydomain.example.abs.Team;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TeamFactoryImplTest {
    
    TeamFactoryImpl teamFactory;

    @Before
    public void setUp() throws Exception {
        teamFactory = new TeamFactoryImpl();
    }

    @Test
    public void givenXMLWithDataInElementsWhenXMLIsBoundEntityContainsCorrectData() throws Exception {
        String xml = "<team>\r\n"
                + "    <project-id>xml-an-bind</project-id>\r\n"
                + "    <person>\r\n"
                + "        <id>3463463</id>\r\n"
                + "        <names>\r\n"
                + "            <firstname>John</firstname>\r\n"
                + "            <lastname>Plenkovich</lastname>\r\n"
                + "        </names>\r\n"
                + "        <physical>\r\n"
                + "            <sex>male</sex>\r\n"
                + "            <age>42</age>\r\n"
                + "        </physical>\r\n"
                + "    </person>\r\n"
                + "    <person>\r\n"
                + "        <id>3456454</id>\r\n"
                + "        <names>\r\n"
                + "            <firstname>Eliza</firstname>\r\n"
                + "            <lastname>Bowl</lastname>\r\n"
                + "        </names>\r\n"
                + "        <physical>\r\n"
                + "            <sex>female</sex>\r\n"
                + "            <age>35</age>\r\n"
                + "        </physical>\r\n"
                + "    </person>\r\n"
                + "    <person>\r\n"
                + "        <id>3456465</id>\r\n"
                + "        <names>\r\n"
                + "            <firstname>John</firstname>\r\n"
                + "            <lastname>Robbin</lastname>\r\n"
                + "        </names>\r\n"
                + "        <physical>\r\n"
                + "            <sex>male</sex>\r\n"
                + "            <age>28</age>\r\n"
                + "        </physical>\r\n"
                + "    </person>\r\n"
                + "</team>";
        Team team = teamFactory.newTeam(toNode(xml));
        assertNotNull(team);
        assertEquals("xml-an-bind", team.getProjectId());
        Collection<Person> persons = team.getPersons();
        assertEquals(3, persons.size());
        Iterator<Person> iterator = persons.iterator();
        assertPerson("3463463", "John", "Plenkovich", Sex.male, 42, iterator.next());
        assertPerson("3456454", "Eliza", "Bowl", Sex.female, 35, iterator.next());
        assertPerson("3456465", "John", "Robbin", Sex.male, 28, iterator.next());
    }

    
    private void assertPerson(String id, String firstname, String lastname, Sex sex, int age, Person person) {
            assertEquals(id, person.getId());
            assertEquals(firstname, person.getFirstname());
            assertEquals(lastname, person.getLastname());
            assertEquals(sex, person.getSex());
            assertEquals(age, person.getAge());
    }

    protected Element toNode(String xml) throws ParserConfigurationException, SAXException, IOException  {
        InputSource inputSource = toInputSource(xml);
        return toNode(inputSource, true);
    }

    protected Element toNode(InputSource inputSource, boolean namespaceAware) throws ParserConfigurationException, SAXException, IOException  {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        if (namespaceAware) {
            factory.setNamespaceAware(true);
        }
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(inputSource);
        return getFirstElement(document);
    }
    
    protected InputSource toInputSource(String xml) {
        Reader reader = new StringReader(xml);
        return new InputSource(reader);
    }
    
    protected Element getFirstElement(Document document) {
        for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                return (Element)node;
            }
        }
        return null;
    }
}
```

Запустим наш тест и убедимся что он *fails*

```textplain
Results :                                                                                                                       Failed tests:   givenXMLWithDataInElementsWhenXMLIsBoundEntityContainsCorrectData(org.mydomain.example.impl.TeamFactoryImplTest): expected:<xml-an-bind> but was:<null>                                                                                                                               Tests run: 1, Failures: 1, Errors: 0, Skipped: 0
```

###### Реализация фабрики Team

```java
package org.mydomain.example.impl;

import org.mydomain.example.abs.FactoryException;
import org.mydomain.example.abs.Team;
import org.shikalenko.xmlanbind.BindException;
import org.shikalenko.xmlanbind.XMLAnnotationBinder;
import org.w3c.dom.Node;

public class TeamFactoryImpl {
    
    public Team newTeam(Node node) throws FactoryException {
        try {
            XMLAnnotationBinder binder = XMLAnnotationBinder.newInstance();
            return binder.bind(TeamImpl.class, node); 
        } catch (org.shikalenko.abs.FactoryException | BindException e) {
            throw new FactoryException(e);
        }
    }
    
}
```

Запустим наш тест и убедимся, что он *success*

```textplain
Running org.mydomain.example.impl.TeamFactoryImplTest                                                                           Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.108 sec                                                       Results :                                                                                                                                              Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
```

#### Получение данных из атрибутов

Немного изменим класс *TeamImpl*

Добавим аннотацию @Attribute к методу setProjectId

```java
    @Element("project-id")
    @Attribute("project-id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
```

Еще раз запустим предыдущий TestCase чтобы убедиться, что ничего не сломалось

```plaintext
Running org.mydomain.example.impl.TeamFactoryImplTest                                                                            Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.107 sec
Results :
						Tests run: 1, Failures: 0, Errors: 0, Skipped: 0 
```

Добавим еще один Test Case, скопировав существующий, изменив его имя и перенеся данные, какие только возможно, в атрибуты.

```java
    @Test
    public void givenXMLWithDataInAttributesWhenXMLIsBoundEntityContainsCorrectData() throws Exception {
        String xml = "<team project-id=\"xml-an-bind\">\r\n"
                + "    <person id=\"3463463\">\r\n"
                + "        <names firstname=\"John\" lastname=\"Plenkovich\"/>\r\n"
                + "        <physical sex=\"male\" age=\"42\"/>\r\n"
                + "    </person>\r\n"
                + "    <person id=\"3456454\">\r\n"
                + "        <names firstname=\"Eliza\" lastname=\"Bowl\"/>\r\n"
                + "        <physical sex=\"female\" age=\"35\"/>\r\n"
                + "    </person>\r\n"
                + "    <person id=\"3456465\">\r\n"
                + "        <names firstname=\"John\" lastname=\"Robbin\"/>\r\n"
                + "        <physical sex=\"male\" age=\"28\"/>\r\n"
                + "    </person>\r\n"
                + "</team>";
        Team team = teamFactory.newTeam(toNode(xml));
        assertNotNull(team);
        assertEquals("xml-an-bind", team.getProjectId());
        Collection<Person> persons = team.getPersons();
        assertEquals(3, persons.size());
        Iterator<Person> iterator = persons.iterator();
        assertPerson("3463463", "John", "Plenkovich", Sex.male, 42, iterator.next());
        assertPerson("3456454", "Eliza", "Bowl", Sex.female, 35, iterator.next());
        assertPerson("3456465", "John", "Robbin", Sex.male, 28, iterator.next());
    }
```

Запустим уже 2 TestCase и убедимся, что они success

```textplain
Running org.mydomain.example.impl.TeamFactoryImplTest                                                                           Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.109 sec                                                       Results :                                                                                                                       						Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
```

#### Использование выражений *XPath*

Изменим класс *TeamImpl* следующим образом

```java
package org.mydomain.example.impl;

import java.util.Collection;
import java.util.LinkedList;

import org.mydomain.example.abs.Person;
import org.mydomain.example.abs.Team;
import org.shikalenko.xmlanbind.annotations.XPath;

public class TeamImpl implements Team {
    private String projectId;
    private Collection<Person> persons = new LinkedList<Person>();
    @Override
    public String getProjectId() {
        return projectId;
    }
    @XPath("@project-id|project-id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    @Override
    public Collection<Person> getPersons() {
        return persons;
    }
    @XPath(type = PersonImpl.class, value = "person")    
    public void setPersons(Collection<Person> persons) {
        this.persons.addAll(persons);
    }
}

```

Немного пояснений.

Мы заменили *@Element("project-id") @Attribute("project-id")* на *@XPath("@project-id|project-id")*.

*XMLAnnotationBinder*, встретив аннотацию *@XPath* вызывает аннотированный метод, передавая в качестве параметра объект возвращенный вычислением XPath выражения. Те кто знакомы с XPath понимают что выражение "@project-id|project-id" выполненное на узле, возвращает *node set* состоящий из от 0 до 2 узлов: атрибута и элемента 'project-id' в зависимости от того какой из них есть в наличии.

Вторая замена полностью изменяет смысл метода *addPerson*. Вместо "*addPerson*" он становится "*setPersons*".  Это сделано потому, что xpath "person" возвращает NodeSet, то есть несколько узлов, столько сколько person у нас в xml, поэтому передаваться будет коллекция объектов указанного в аннотации типа. Так как каждый из элементов этой коллекции будет заполняться *XMLAnnotationBinder*, то в параметре *type* нужно передавать не класс интерфейса, который указан в generic определении коллекции, а класс реализации, которую *XMLAnnotationBinder* знает как заполнять. Иначе метод не будет вызван.

Попробуем теперь запустить наши Test Cases. Мы увидим, что каждый из них производит следующую ошибку

```plaintext
org.mydomain.example.abs.FactoryException: org.shikalenko.xmlanbind.BindException: Class org.mydomain.example.impl.TeamImpl uses annotation @XPath. Call setFeature(XMLAnnotationBinder.FEATURE_XPATH) before using XMLAnnotationBinder instance	
```

До использования @XPath аннотации надо предупредить XMLAnnotationBinder об этом путем вызова setFeature(XMLAnnotationBinder.FEATURE_XPATH) на инстансе XMLAnnotationBinder

Добавим такую строку в нашу фабрику.

```java
package org.mydomain.example.impl;

import org.mydomain.example.abs.FactoryException;
import org.mydomain.example.abs.Team;
import org.shikalenko.xmlanbind.BindException;
import org.shikalenko.xmlanbind.XMLAnnotationBinder;
import org.w3c.dom.Node;

public class TeamFactoryImpl {
    
    public Team newTeam(Node node) throws FactoryException {
        try {
            XMLAnnotationBinder binder = XMLAnnotationBinder.newInstance();
            binder.setFeature(XMLAnnotationBinder.FEATURE_XPATH);
            return binder.bind(TeamImpl.class, node); 
        } catch (org.shikalenko.abs.FactoryException | BindException e) {
            throw new FactoryException(e);
        }
    }
}
```

Запустив тесты убеждаемся, что оба Test Cases выполняются успешно.

Окончательный вариант этого примера [смотри git ](../../../tree/main/xml-an-bind/example/team)

#### Обработка XML с Namespace

Обработка XML с namespace ничем не отличается от обработки xml без **namespace**, пока вы не используете **xpath**.

Если вы используете xpath, выражения xpath зависят от того, как вы создаете org.w3c.dom.Node:

1. с выключеной опцией Namespaceaware
2. с включеной опцией Namespaceaware например используя javax.xml.parsers.DocumentBuilderFactory.setNamespaceAware(true)

В случае 1 **НЕОБХОДИМО** использовать namespace префиксы в xpath выражениях так как в этом случае полное **qname** то есть **prefix:localName** трактуется как имя узла. Префикс должно быть точно таким же каким он используется в обрабатываемом XML. И его не должно быть если XML узлы не имеют префикса т.е. используют namespace по умолчанию, определенное в атрибуте **xmlns**.

В случае 2 **НЕ НАДО** использовать namespace префиксы в xpath выражениях. Только localName должны участвовать в выражении.

#### Правила умалчивания:

Если метод не аннотированный, но его имя состоит из префикса *"get"*, а оставшаяся часть совпадает или с именем child элемента или с именем атрибута рассматриваемого node, в которых первый символ приведен к верхнему регистру, то в этот метод передается содержимое child элемента или атрибута соответственно.

