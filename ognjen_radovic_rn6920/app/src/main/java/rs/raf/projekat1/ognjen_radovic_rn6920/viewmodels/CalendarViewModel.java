    package rs.raf.projekat1.ognjen_radovic_rn6920.viewmodels;



    import androidx.lifecycle.LiveData;
    import androidx.lifecycle.MutableLiveData;
    import androidx.lifecycle.Transformations;
    import androidx.lifecycle.ViewModel;

    import java.time.DayOfWeek;
    import java.time.LocalDate;
    import java.time.LocalTime;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

    import rs.raf.projekat1.ognjen_radovic_rn6920.models.Day;
    import rs.raf.projekat1.ognjen_radovic_rn6920.models.Priority;
    import rs.raf.projekat1.ognjen_radovic_rn6920.models.Task;

    public class CalendarViewModel extends ViewModel {

        public static int counter = 101;

        private final MutableLiveData<List<Day>> days = new MutableLiveData<>();
        private ArrayList<Day> dayList = new ArrayList<>();

        public CalendarViewModel() {
            LocalDate currentDate = LocalDate.now();
            int x=generateFirstDay();
            // Add N days to the current date
            int N = 7; // Or any other value you want to add
            while(x!=0){
                Day day=new Day(true);
                LocalDate date = LocalDate.of(1970, 1, 1).plusDays(x);
                day.setDate(date);
                dayList.add(day);
                days.setValue(dayList);
                x--;
            }
            for (int i = 0; i <= 365; i++) {
                LocalDate futureDate = currentDate.plusDays(i);
                Day day = new Day(futureDate);
                if(day!=null){
                    LocalTime start1 = LocalTime.of(21, 0);
                    LocalTime end1 = LocalTime.of(21, 30);
                    LocalTime start2 = LocalTime.of(14, 0);
                    LocalTime end2 = LocalTime.of(15, 30);
                    LocalTime start3 = LocalTime.of(14, 0);
                    LocalTime end3 = LocalTime.of(15, 30);
                    if(i%9==0){
                        day.addTask(new Task("Task 2",day,start2,end2,"Ovde treba da stoji deskripcija taska", Priority.LOW));
                        dayList.add(day);
                        days.setValue(dayList);
                    }
                    else if(i%3==0){
                        day.addTask(new Task("Primer task",day,start1,end1,"Ovde treba da stoji deskripcija taska", Priority.MEDIUM));
                        day.addTask(new Task("Task 2",day,start2,end2,"Ovde treba da stoji deskripcija taska", Priority.LOW));
                        day.addTask(new Task("Task 3",day,start3,end3,"Ovde treba da stoji deskripcija taska", Priority.HIGH));
                        dayList.add(day);
                        days.setValue(dayList);
                    }
                    else{
                        day.addTask(new Task("Primer task",day,start1,end1,"Ovde treba da stoji deskripcija taska", Priority.MEDIUM));
                        day.addTask(new Task("Task 2",day,start2,end2,"Ovde treba da stoji deskripcija taska", Priority.LOW));
                        dayList.add(day);
                        days.setValue(dayList);
                    }
                    }


                }
            ArrayList<Day> listToSubmit = new ArrayList<>(dayList);
            days.setValue(listToSubmit);
            }
            // We are doing this because cars.setValue in the background is first checking if the reference on the object is same
            // and if it is it will not do notifyAll. By creating a new list, we get the new reference everytime







        private int generateFirstDay(){
            LocalDate currentDate = LocalDate.now();
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            int x;

            switch(dayOfWeek) {
                case SUNDAY:
                    x = 6; // Sunday
                    break;
                case MONDAY:
                    x = 0; // Monday
                    break;
                case TUESDAY:
                    x = 1; // Tuesday
                    break;
                case WEDNESDAY:
                    x = 2; // Wednesday
                    break;
                case THURSDAY:
                    x = 3; // Thursday
                    break;
                case FRIDAY:
                    x = 4; // Friday
                    break;
                case SATURDAY:
                    x = 5; // Saturday
                    break;
                default:
                    // Handle unexpected values
                    x = -1;
                    break;
            }

            return x; // Output the value of x to the console

        }

        public LiveData<List<Day>> getDays() {
            return days;
        }
        public void updateDay(Day day) {
            int index1 = -1;
            for (int i = 0; i < dayList.size(); i++) {
                LocalDate date1 = day.getDate();
                LocalDate date2 = dayList.get(i).getDate();
                if (date1 != null && date2 != null&& date1.isEqual(date2)) {
                    index1 = i;
                    break;
                }
            }
            if (index1 != -1) {
                dayList.set(index1, day);
                ArrayList<Day> listToSubmit = new ArrayList<>(dayList);
                days.setValue(listToSubmit);
            }
        }

        public LiveData<Day> getDayForTask(Task task) {
            // Find the Day object that corresponds to the Task
            Optional<Day> optionalDay = dayList.stream()
                    .filter(day -> day.getTasks().contains(task))
                    .findFirst();

            // If the Day object is found, return it as a LiveData object
            if (optionalDay.isPresent()) {
                Day day = optionalDay.get();
                return new MutableLiveData<>(day);
            } else {
                // If the Day object is not found, return null
                return null;
            }
        }

        public void updateTask(LocalDate date, Task task1,Task task2) {
            Optional<Day> optionalDay = dayList.stream()
                    .filter(day -> isDateEqual(day, date))
                    .findFirst();

            if (optionalDay.isPresent()) {
                Day day = optionalDay.get();
                int i=0;
                for (Task task : day.getTasks()) {
                    if (task.equals(task1)) {
                        List<Task> tasks=day.getTasks();
                        task2.setId(i);
                        tasks.set(i,task2);
                        day.setTasks(tasks);
                    }
                    i++;
                }
                updateDay(day);
            } else {
                System.out.println("Day not found.");
            }
        }

        public void deleteTask(LocalDate date,int id) {
            Optional<Day> optionalDay = dayList.stream()
                    .filter(day -> isDateEqual(day, date))
                    .findFirst();

            if (optionalDay.isPresent()) {
                Day day = optionalDay.get();
                int i=0;
                day.getTasks().remove(i);
                updateDay(day);
            } else {
                System.out.println("Day not found.");
            }
        }

        public void addTaskToDay(LocalDate date, Task task) {
            Optional<Day> optionalDay = dayList.stream()
                    .filter(day -> isDateEqual(day, date))
                    .findFirst();

            if (optionalDay.isPresent()) {
                Day day = optionalDay.get();
                day.addTask(task);
                updateDay(day);
            }
        }







        public LiveData<Day> getDayByDate(LocalDate date) {
            return Transformations.map(days, dayList -> {
                if(dayList == null) {
                    return null;
                }
                Optional<Day> optionalDay = dayList.stream()
                        .filter(day -> isDateEqual(day, date))
                        .findFirst();
                return optionalDay.orElse(null);
            });
        }

        public Day getDayByDateNonLive(LocalDate date) {
            if(dayList == null) {
                return null;
            }
            Optional<Day> optionalDay = dayList.stream()
                    .filter(day -> isDateEqual(day, date))
                    .findFirst();
            return optionalDay.orElse(null);
        }
        public boolean isDateEqual(Day day, LocalDate date) {
            return day != null && day.getDate() != null && day.getDate().isEqual(date);
        }



        //    public void filterCars(String filter) {
    //        List<Car> filteredList = dayList.stream().filter(car -> car.getManufacturer().toLowerCase().startsWith(filter.toLowerCase())).collect(Collectors.toList());
    //        cars.setValue(filteredList);
    //    }

    //    public int addCar(String pictureUrl, String manufacturer, String model) {
    //        int id = counter++;
    //        Car car = new Car(id, pictureUrl, manufacturer, model);
    //        carList.add(car);
    //        ArrayList<Car> listToSubmit = new ArrayList<>(carList);
    //        cars.setValue(listToSubmit);
    //        return id;
    //    }

    //    public void removeCar(int id) {
    //        Optional<Car> carObject = carList.stream().filter(car -> car.getId() == id).findFirst();
    //        if (carObject.isPresent()) {
    //            carList.remove(carObject.get());
    //            ArrayList<Car> listToSubmit = new ArrayList<>(carList);
    //            cars.setValue(listToSubmit);
    //        }
    //    }

    }
