import exceptions.*;
import java.util.Calendar;
/**
 * Contatto
 */
public class Contatto {

    public static final String NOMECOGNOME_REGEXP = "[\\w]*";
    public static final String NUMERODITELFONO_REGEXP = "[0-9]{10}";
    public static final String DATA_REGEXP = "^\\d{4}-\\d{2}-\\d{2}$";
    private String name,surname,numeroDiTelefono; // name and surname as string without symbols; digits from 0 to 9 (10 times)
    private Calendar dataNascita = Calendar.getInstance();

    public String getDataNascita() {
        return String.format("%s-%s-%s",dataNascita.get(Calendar.YEAR),dataNascita.get(Calendar.MONTH)+1,dataNascita.get(Calendar.DAY_OF_MONTH));
    }

    public void setDataNascita(String dataNascita) throws DateInvalidException {
        if(dataNascita.matches(DATA_REGEXP))
            throw new DateInvalidException("Invalid date");
        String[] splittedDate = dataNascita.split("-"); // yyyy-mm-dd
        int[] intSplittedDate = new int[3];
        for (int i = 0; i < 3; i++) {
            intSplittedDate[i]=Integer.parseInt(splittedDate[i]);
        }
        
        this.dataNascita.set(Calendar.YEAR, intSplittedDate[0]>=1900 ? intSplittedDate[0]:1900);
        this.dataNascita.set(Calendar.MONTH, intSplittedDate[1]-1);
        this.dataNascita.set(Calendar.DAY_OF_MONTH, intSplittedDate[2]);
    }

    public Contatto(String nome, String cognome, String numeroDiTelefono,String data) throws DateInvalidException,NameInvalidException,SurnameInvalidException,PhoneInvalidException {
        System.out.println(nome+" "+cognome+" "+numeroDiTelefono+" "+data);
        System.out.println(data.matches(DATA_REGEXP));
        if(! nome.matches(NOMECOGNOME_REGEXP))
            throw new NameInvalidException("invalid name");
        if(! cognome.matches(NOMECOGNOME_REGEXP))
            throw new SurnameInvalidException("invalid surname");
        if(! numeroDiTelefono.matches(NUMERODITELFONO_REGEXP))
            throw new PhoneInvalidException("Invalid phone");
        if(! data.matches(DATA_REGEXP))
            throw new DateInvalidException("Invalid date");
        this.name = nome;
        this.surname = cognome;
        this.numeroDiTelefono = numeroDiTelefono;
        String[] splittedDate = data.split("-");
        int[] intSplittedDate = new int[3];
        for (int i = 0; i < 3; i++) {
            intSplittedDate[i]=Integer.parseInt(splittedDate[i]);
        }
        
        this.dataNascita.set(Calendar.YEAR, intSplittedDate[0]>=1900 ? intSplittedDate[0]:1900);
        this.dataNascita.set(Calendar.MONTH, intSplittedDate[1]-1);
        this.dataNascita.set(Calendar.DAY_OF_MONTH, intSplittedDate[2]);
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Contatto(name, surname, numeroDiTelefono,dataNascita.toString());
    }
}