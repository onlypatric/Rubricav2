import exceptions.*;
/**
 * Contatto
 */
public class Contact {

    private String name,surname,numeroDiTelefono; // name and surname as string without symbols; digits from 0 to 9 (10 times)

    public static final String NOMECOGNOME_REGEXP = "[\\w]*";
    public static final String NUMERODITELFONO_REGEXP = "[0-9]{10}";

    public Contact(String nome, String cognome, String numeroDiTelefono) throws NameInvalidException,SurnameInvalidException,PhoneInvalidException {
        if(! nome.matches(NOMECOGNOME_REGEXP))
            throw new NameInvalidException("invalid name");
        if(! cognome.matches(NOMECOGNOME_REGEXP))
            throw new SurnameInvalidException("invalid surname");
        if(! numeroDiTelefono.matches(NUMERODITELFONO_REGEXP))
            throw new PhoneInvalidException("Numero di telefono non valido");
        this.name = nome;
        this.surname = cognome;
        this.numeroDiTelefono = numeroDiTelefono;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) throws NameInvalidException {
        if(! nome.matches(NOMECOGNOME_REGEXP))
            throw new NameInvalidException("invalid name");
        this.name = nome;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String cognome) throws SurnameInvalidException  {
        if(! cognome.matches(NOMECOGNOME_REGEXP))
            throw new SurnameInvalidException("invalid surname");
        this.surname = cognome;
    }

    public String getNumeroDiTelefono() {
        return numeroDiTelefono;
    }

    public void setNumeroDiTelefono(String numeroDiTelefono) throws PhoneInvalidException  {
        if(! numeroDiTelefono.matches(NUMERODITELFONO_REGEXP))
            throw new PhoneInvalidException("Numero di telefono non valido");
        this.numeroDiTelefono = numeroDiTelefono;
    } 

    @Override
    public String toString() {
        return "Contatto [nome=" + name + ", cognome=" + surname + ", numeroDiTelefono=" + numeroDiTelefono + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        result = prime * result + ((numeroDiTelefono == null) ? 0 : numeroDiTelefono.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Contact other = (Contact) obj;
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Contact(name, surname, numeroDiTelefono);
    }
}