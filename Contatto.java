import exceptions.*;
import java.util.Calendar;

/**
 * The `Contatto` class represents a contact with a name, surname, phone number, and birthdate.
 * It provides methods to set and retrieve these attributes and includes validations for each attribute.
 * 
 * The class includes regular expressions for name, surname, phone number, and date validation.
 * It also provides methods to validate and set the birthdate using the specified date format.
 * 
 * @author Pintescul Patric
 * @version 2.0
 */
public class Contatto {

    public static final String NOMECOGNOME_REGEXP = "[\\w]*";
    public static final String NUMERODITELFONO_REGEXP = "[0-9]{10}";
    public static final String DATA_REGEXP = "^\\d{4}-\\d{1,2}-\\d{1,2}$";

    private String name, surname, phoneNumber;
    private Calendar birthDate = Calendar.getInstance();

    /**
     * Constructs a new `Contatto` object with the provided name, surname, phone
     * number, and birthdate.
     *
     * @param name             The name of the contact.
     * @param surname          The surname of the contact.
     * @param phoneNumber The phone number of the contact.
     * @param date             The birthdate of the contact in "yyyy-MM-dd" format.
     * @throws DateInvalidException    If the provided birthdate is invalid.
     * @throws NameInvalidException    If the provided name is invalid.
     * @throws SurnameInvalidException If the provided surname is invalid.
     * @throws PhoneInvalidException   If the provided phone number is invalid.
     */
    public Contatto(String name, String surname, String phoneNumber, String dateString)
            throws DateInvalidException, NameInvalidException, SurnameInvalidException, PhoneInvalidException {

        if (!name.matches(NOMECOGNOME_REGEXP))
            throw new NameInvalidException("Invalid name");
        if (!surname.matches(NOMECOGNOME_REGEXP))
            throw new SurnameInvalidException("Invalid surname");
        if (!phoneNumber.matches(NUMERODITELFONO_REGEXP))
            throw new PhoneInvalidException("Invalid phone number");
        if (!dateString.matches(DATA_REGEXP))
            throw new DateInvalidException("Invalid date");

        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;

        String[] splittedDate = dateString.split("-");
        int[] intSplittedDate = new int[3];
        for (int i = 0; i < 3; i++) {
            intSplittedDate[i] = Integer.parseInt(splittedDate[i]);
        }

        this.birthDate.set(Calendar.YEAR, intSplittedDate[0] >= 1900 ? intSplittedDate[0] : 1900);
        this.birthDate.set(Calendar.MONTH, intSplittedDate[1] - 1);
        this.birthDate.set(Calendar.DAY_OF_MONTH, intSplittedDate[2]);
    }

    /**
     * Gets the formatted birthdate in the format "yyyy-MM-dd".
     *
     * @return The formatted birthdate.
     */
    public String getBirthDate() {
        return String.format("%s-%s-%s", birthDate.get(Calendar.YEAR), birthDate.get(Calendar.MONTH) + 1,
                birthDate.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Sets the birthdate based on the provided string in the format "yyyy-MM-dd".
     *
     * @param dataNascita The birthdate string in "yyyy-MM-dd" format.
     * @throws DateInvalidException If the provided date is invalid.
     */
    public void setDataNascita(String dataNascita) throws DateInvalidException {
        if (!dataNascita.matches(DATA_REGEXP))
            throw new DateInvalidException("Invalid date");

        String[] splittedDate = dataNascita.split("-");
        int[] intSplittedDate = new int[3];
        for (int i = 0; i < 3; i++) {
            intSplittedDate[i] = Integer.parseInt(splittedDate[i]);
        }

        this.birthDate.set(Calendar.YEAR, intSplittedDate[0] >= 1900 ? intSplittedDate[0] : 1900);
        this.birthDate.set(Calendar.MONTH, intSplittedDate[1] - 1);
        this.birthDate.set(Calendar.DAY_OF_MONTH, intSplittedDate[2]);
    }

    /**
     * Gets the name of the contact.
     *
     * @return The name of the contact.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the contact.
     *
     * @param nome The name to set for the contact.
     * @throws NameInvalidException If the provided name is invalid.
     */
    public void setName(String nome) throws NameInvalidException {
        if (!nome.matches(NOMECOGNOME_REGEXP))
            throw new NameInvalidException("Invalid name");
        this.name = nome;
    }

    /**
     * Gets the surname of the contact.
     *
     * @return The surname of the contact.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the contact.
     *
     * @param cognome The surname to set for the contact.
     * @throws SurnameInvalidException If the provided surname is invalid.
     */
    public void setSurname(String cognome) throws SurnameInvalidException {
        if (!cognome.matches(NOMECOGNOME_REGEXP))
            throw new SurnameInvalidException("Invalid surname");
        this.surname = cognome;
    }

    /**
     * Gets the phone number of the contact.
     *
     * @return The phone number of the contact.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the contact.
     *
     * @param numeroDiTelefono The phone number to set for the contact.
     * @throws PhoneInvalidException If the provided phone number is invalid.
     */
    public void setPhoneNumber(String numeroDiTelefono) throws PhoneInvalidException {
        if (!numeroDiTelefono.matches(NUMERODITELFONO_REGEXP))
            throw new PhoneInvalidException("Invalid phone number");
        this.phoneNumber = numeroDiTelefono;
    }

    /**
     * Returns a string representation of the `Contatto` object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "Contatto [nome=" + name + ", cognome=" + surname + ", numeroDiTelefono=" + phoneNumber + "]";
    }

    /**
     * Computes a hash code for the `Contatto` object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        return result;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj The reference object with which to compare.
     * @return `true` if this object is the same as the obj argument; `false`
     *         otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Contatto other = (Contatto) obj;

        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;

        if (surname == null) {
            if (other.surname != null)
                return false;
        } else if (!surname.equals(other.surname))
            return false;

        return true;
    }

    /**
     * Creates and returns a copy of this object.
     *
     * @return A clone of this instance.
     * @throws CloneNotSupportedException If the object's class does not support the
     *                                    Cloneable interface.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Contatto(name, surname, phoneNumber, birthDate.toString());
    }
}