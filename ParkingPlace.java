/**
 * Klasa ParkingPlace
 */

public class ParkingPlace {

        private String type;
        private int number;

        /**
         * Konstruktory
         * @param type
         * @param number
         */

        public ParkingPlace(String type, int number) {
            this.type = type;
            this.number = number;
        }

        /**
         * Getter klasy ParkingPlace
         * @return
         */

        public String getType() {
            return type;
        }

        /**
         * Setter klasy ParkingPlace
         * @return
         */

        public void setType(String type) {
            this.type = type;
        }

        /**
         * Getter klasy ParkingPlace
         * @return
         */

        public int getNumber() {
            return number;
        }

        /**
         * Setter klasy ParkingPlace
         * @return
         */

        public void setNumber(int number) {
            this.number = number;
        }

        /**
         * Metoda toString klasy ParkingPlace
         * @return
         */

        @Override
        public String toString() {
            return "Miejsce postojowe " + type + " " + "nr: " + number;
        }
    }


