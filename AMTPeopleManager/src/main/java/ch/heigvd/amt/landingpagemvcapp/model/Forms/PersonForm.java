package ch.heigvd.amt.landingpagemvcapp.model.Forms;

import ch.heigvd.amt.landingpagemvcapp.model.Enums.Gender;
import ch.heigvd.amt.landingpagemvcapp.model.Person;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class PersonForm
{
	private static final String GENDER_INPUT   = "genderUser";
	private static final String FIRST_NAME_INPUT   = "firstNameUser";
	private static final String LAST_NAME_INPUT   = "lastNameUser";
	private static final String BIRTHDAY_INPUT   = "birthdayUser";
	private static final String EMAIL_INPUT  = "emailUser";
	private static final String PHONE_INPUT  = "phoneUser";

	private String              result;
	private Map<String, String> errors      = new HashMap<String, String>();

	public String getResult() {
		return result;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public Person addPerson(HttpServletRequest request )
	{
		String gender = getValueInput( request, GENDER_INPUT );
		String firstName = getValueInput( request, FIRST_NAME_INPUT);
		String lastName = getValueInput( request,  LAST_NAME_INPUT);
		String email = getValueInput( request, EMAIL_INPUT );
		String phone = getValueInput( request, PHONE_INPUT );
		String birthday = getValueInput( request, BIRTHDAY_INPUT );




		Person person = new Person();

		try {
			validateNotEmpty( gender );
		} catch ( Exception e ) {
			setError( GENDER_INPUT, e.getMessage() );
		}
		if(gender.equals("Men"))
		{
			person.setGender(Gender.Men);
		}
		else if(gender.equals("Women"))
		{
			person.setGender(Gender.Women);
		}

		try {
			validateNotEmpty( firstName );
		} catch ( Exception e ) {
			setError( FIRST_NAME_INPUT, e.getMessage() );
		}
		person.setFirstName( firstName );

		try {
			validateNotEmpty(lastName);
		} catch ( Exception e ) {
			setError( LAST_NAME_INPUT, e.getMessage() );
		}
		person.setLastName(lastName);


		try {
			validateNotEmpty( birthday );
		} catch ( Exception e ) {
			setError( BIRTHDAY_INPUT, e.getMessage() );
		}
		person.setBirthday( birthday );


		try {
			validateEmail( email );
		} catch ( Exception e ) {
			setError( EMAIL_INPUT, e.getMessage() );
		}
		person.setEmail( email );

		try {
			validatePhone( phone );
		} catch ( Exception e ) {
			setError( PHONE_INPUT, e.getMessage() );
		}
		person.setPhone( phone );

		return person;

	}

	private void validateEmail(String email) throws Exception
	{
		if (email != null && email.trim().length() != 0)
		{



			if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)"))
			{
				throw new Exception("Please enter a correct email address");
			}
		}
		else
		{
			throw new Exception("Please enter a email address");
		}
	}

	private void validatePhone(String phone) throws Exception
	{
		if (phone == null || phone.length() < 9 || phone.length() > 13)
		{
			throw new Exception("Enter a correct phone number with ");
		}
	}

	private void validateNotEmpty(String data) throws Exception
	{
		if (data == null || data.trim().length() < 3)
		{
			throw new Exception("Incorrect data (min 3 letters)");
		}
	}


	private void setError( String champ, String message ) {
		errors.put( champ, message );
	}


	private static String getValueInput( HttpServletRequest request, String inputName ) {
		String value = request.getParameter( inputName );
		if ( value == null || value.trim().length() == 0 ) {
			return null;
		} else {
			return value.trim();
		}
	}
}
