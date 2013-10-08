package com.codetutr.controller;

import com.byteslounge.spring.tx.model.User;
import com.byteslounge.spring.tx.user.UserManager;
import com.codetutr.domain.Person;
import com.codetutr.service.PersonService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Controller
@Transactional
@RequestMapping("api")
public class PersonController {
	
	PersonService personService;

	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}

    @RequestMapping(value="delete")
    @ResponseBody
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String deletePatient(User user) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        UserManager userManager = (UserManager) ctx.getBean("userManagerImpl");


        //user.setUsername("johndoe");
        //user.setSocialSecurityNumber(socialSecurityNumber);

//      user.setName("johndoe");
        try{
            userManager.deleteUser(user);
        }
        catch(Exception e){
            e.printStackTrace();
            return user.getSocialSecurityNumber();

        }

        //userManager.deleteUser(user);
        System.out.println("User deleted!");

        //user = userManager.getUser(ssn);

        System.out.println("\nUser fetched by username!"
                // + "\nId: " + user.getId()
                // + "\nUsername: " + user.getUsername()
                + "\nName: " );


        return "Deleted patient: " + user.getFirstName() + " " + user.getLastName() + " " +user.getSocialSecurityNumber();
    }

	@RequestMapping("person/random")
	@ResponseBody
	public Person randomPerson() {
		return personService.getRandom();
	}

	@RequestMapping("person/{id}")
	@ResponseBody
	public Person getById(@PathVariable Long id) {
		return personService.getById(id);
	}

	/* same as above method, but is mapped to
	 * /api/person?id= rather than /api/person/{id}
	 */
	@RequestMapping(value="person", params="id")
	@ResponseBody
	public Person getByIdFromParam(@RequestParam("id") Long id) {
		return personService.getById(id);
	}
    /**
	 * Saves new person. Spring automatically binds the name
	 * and age parameters in the request to the person argument
	 * @param person
	 * @return String indicating success or failure of save
	 */
	@RequestMapping(value="person", method=RequestMethod.POST)
	@ResponseBody
	public String savePerson(Person person) {
		personService.save(person);
		return "Saved person: " + person.toString();
	}
    @RequestMapping("person/user/{ssn}")
    @ResponseBody
    @Cacheable(value="Users")
    public User getPatient(@PathVariable String ssn) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        UserManager userManager = (UserManager) ctx.getBean("userManagerImpl");

        User user = new User();
       // user.setUsername("johndoe");
        user.setSocialSecurityNumber(ssn);

//      user.setName("johndoe");
//        userManager.insertUser(user);

        //System.out.println("User inserted!");

        user = userManager.getUser(ssn);
        if (user == null){
            System.out.println("did not find home.jsp = " + ssn);
            user = new User();
            user.setSocialSecurityNumber(ssn);

            return user;
        }

        System.out.println("\nUser fetched by username!"
                //+ "\nId: " + user.getId()
               // + "\nUsername: " + user.getUsername()
                + "\nName: " + user.getFirstName());

        //user = userManager.getUserById(user.getId());

//        System.out.println("\nUser fetched by ID!"
//                + "\nId: " + user.getId()
//                + "\nUsername: " + user.getUsername()
//                + "\nName: " + user.getFirstName());
//
//        List<User> users = userManager.getUsers();
//
//        System.out.println("\nUser list fetched!"
//                + "\nUser count: " + users.size());

        return user;
    }
    //@RequestMapping(value="person", method=RequestMethod.POST)
    @RequestMapping(value="user", method=RequestMethod.POST)
    @ResponseBody
    public String updatePatient(User user)  {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        UserManager userManager = (UserManager) ctx.getBean("userManagerImpl");

        //user = new User();
       //user.setUsername("johndoe");
        //user.setSocialSecurityNumber(ssn);

      //user.setName("johndoe");
        try{
        userManager.insertUser(user);
        }
        catch (DuplicateKeyException e) {
            return "Patient Already Exists: " + user.getFirstName() + " " + user.getLastName() + " " + user.getSocialSecurityNumber();
        }
        catch (DataIntegrityViolationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return "Patient Already Exists: " + user.getFirstName() + " " + user.getLastName() + " " + user.getSocialSecurityNumber();
        }
        catch (MySQLIntegrityConstraintViolationException e) {
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return "Patient Already Exists: " + user.getFirstName() + " " + user.getLastName() + " " + user.getSocialSecurityNumber();
        }
         catch (ConstraintViolationException e) {
             e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             return "Patient Already Exists: " + user.getFirstName() + " " + user.getLastName() + " " + user.getSocialSecurityNumber();
         }
        catch (Exception e) {
            return "Patient Already Exists: " + user.getFirstName() + " " + user.getLastName();
        }

        System.out.println("User inserted!");

        //user = userManager.getUser(ssn);

        System.out.println("\nUser fetched by username!"
               // + "\nId: " + user.getId()
                //+ "\nUsername: " + user.getUsername()
                + "\nName: " + user.getFirstName());

        //user = userManager.getUserById(user.getId());

//        System.out.println("\nUser fetched by ID!"
//                + "\nId: " + user.getId()
//                + "\nUsername: " + user.getUsername()
//                + "\nName: " + user.getFirstName());
//
//        List<User> users = userManager.getUsers();
//
//        System.out.println("\nUser list fetched!"
//                + "\nUser count: " + users.size());
        return "Saved patient: " + user.getFirstName() + " " + user.getLastName();
        //return user;
    }
}
