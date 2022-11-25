import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            IpValid(scanner);
            validFile(scanner);
        }
    }

    //    Пусть ip адрес задается с консоли.Программа должна проверять валидность введенного ip адреса с
//    помощью регулярного выражения и выводить результат проверки на экран.
    public static void IpValid(Scanner scanner) {
        System.out.println("Enter IP address");
        String ip = scanner.nextLine();
        Pattern pattern = Pattern.compile("((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)");
        Matcher matcher = pattern.matcher(ip);
        boolean rez = matcher.matches();
        if (rez == true) {
            System.out.println("ip address is valid");
        } else {
            System.out.println("ip address isn't correct");
        }
    }

    public static void validFile(Scanner scanner) {
        System.out.println("enter directory:");
        String direct = scanner.nextLine();
        System.out.println("enter number of files to be processed:");
        int count = scanner.nextInt();
        Map<String, Document> finalMap = searchInfoAtFile(listFiles(direct), count);
        for (var elem:
             finalMap.entrySet()) {
            System.out.println(elem.getKey() + " " + elem.getValue());
            
        }

    }

    public static List<String> listFiles(String direct) {
        List<String> listFiles;
        listFiles = Stream.of(new File(direct).listFiles()).filter(file -> !file.isDirectory()).map(File::getAbsolutePath).collect(Collectors.toList());
        if (listFiles == null){
            System.out.println("There are no files in the directory");
        }
        return listFiles;
    }

    public static Map<String, Document> searchInfoAtFile(List<String> listFiles, int count) {
        Pattern patternEmail = Pattern.compile("[a-zA-Z].*@\\w+\\.[a-z]{2,6}");
        Pattern patternPhone = Pattern.compile("\\+\\(\\d{2}\\)\\d{7}");
        Pattern patternDocNum = Pattern.compile("(\\d{4}-[a-zA-Zа-яА-Я]{3}-){2}\\d[a-zA-Zа-яА-Я]\\d[a-zA-Zа-яА-Я]");
        // ArrayList<String> email = new ArrayList<>(), phoneNum = new ArrayList<>(), docNum = new ArrayList<>();
        Document document = new Document();
        Map<String, Document> infoDocs = new HashMap<>();
        List<String> valid = listFiles.stream().filter(file -> file.endsWith(".txt")).limit(count).collect(Collectors.toList());
        if(valid ==null){
            System.out.println("There are no TXT-files in the directory");
            return null;
        }
        int processCount = 0;
        for (String file : valid) {
            String read = null;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                while ((read = br.readLine()) != null) {
                    Matcher matcherEmail = patternEmail.matcher(read);
                    Matcher matcherPhone = patternPhone.matcher(read);
                    Matcher matcherDocNum = patternDocNum.matcher(read);
                    if (matcherEmail.find()) {
                        document.setEmail(matcherEmail.group());
                    }
                    if (matcherPhone.find()) {
                        document.setPhone(matcherPhone.group());
                    }
                    if (matcherDocNum.find()) {
                        document.setDocNum(matcherDocNum.group());
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            infoDocs.put(file, document);
            processCount++;
        }
        if (processCount == listFiles.stream().count()) {
            System.out.println("processed all documents");
        }
        if (processCount < listFiles.stream().count()) {
            System.out.println("processed "
                    + processCount
                    + " documents from "
                    + listFiles.stream().count());
        }

        return infoDocs;
    }
}

   /*   Дополнительное задание
        2. Программа на вход получает путь к папке (задается через консоль).
        В заданной папке находятся текстовые файлы (формат тхт).
        Каждый файл содержит произвольный текст. В этом тексте может быть
        номер документа(один или несколько), емейл и номер телефона.
        номер документа в формате: xxxx-yyy-xxxx-yyy-xyxy, где x - это
        любая цифра, а y - это любая буква русского или латинского алфавита
        номер телефона в формате: +(ХХ)ХХХХХХХ
        Документ может содержать не всю информацию, т.е. например, может не
        содержать номер телефона, или другое поле.
        Необходимо извлечь информацию из N текстовых документов. Число
        документов для обработки N задается с консоли.
        Если в папке содержится меньше документов, чем заданное число -
        следует обрабатывать все документы.
        Извлеченную информацию необходимо сохранить в следующую
        структуру данных:
        Map<String, Document>, где
        ключ типа String - это имя документа без расширения,
        значение типа Document - объект кастомного класса, поля которого
        содержат извлеченную из текстового документа информацию
        Учесть вывод сообщений на случаи если,
        - на вход передан путь к папке, в которой нет файлов
        - все файлы имеют неподходящий формат (следует обрабатывать
        только тхт файлы)
        - так же сообщения на случай других исключительных ситуаций
        TeachMeSkills.by
        В конце работы программы следует вывести сообщение о том, сколько
        документов обработано и сколько было документов невалидного
        формата.*/