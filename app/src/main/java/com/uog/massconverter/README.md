# **Imperial Converter App**

## **Features**
- **Comprehensive Unit Conversion**: Convert seamlessly between a wide range of imperial units, 
    including smaller units (e.g., twip, point, pica) and larger ones (e.g., foot, yard, mile).
- **Theme Support**: Switch between **Light Mode** and **Dark Mode** for a comfortable user experience in any lighting condition.
- **Interactive Conversion Guide**: Access a detailed guide explaining conversion factors for all supported imperial units, 
    making it easier to understand the calculations.
- **Responsive Design**: Optimized for both small and large screen sizes, ensuring usability across various Android devices.
- **Error Handling**: Built-in validation to reject invalid or negative inputs, ensuring accurate results every time.
- **User-Friendly Interface**: Simple and clean layout designed with Jetpack Compose for an intuitive user experience.

---

## **Code Structure**

### **MainActivity.kt**
- Acts as the app's entry point.
- Sets up **navigation** using Jetpack Compose's `NavHost` to handle transitions between screens (Home Screen and Main Screen).

### **HomeScreen.kt**
- Provides the **welcome screen** with:
    - A large, friendly introductory message.
    - An interactive button to navigate to the main conversion screen.
    - A visually appealing background and themed content elements.

### **MainScreen.kt**
- Implements the **conversion functionality**:
    - Accepts numeric input for the value to be converted.
    - Allows users to select input and output units through interactive dropdown menus.
    - Displays the converted result with precision, formatted to two decimal places.
- Features **options menu**:
    - Access to the Conversion Guide dialog.
    - Toggle between Light Mode and Dark Mode.

### **Additional Code Highlights**
- **Dropdown Menus**: Interactive dropdowns for selecting units, implemented using Jetpack Compose’s `DropdownMenu`.
- **Conversion Logic**:
    - A centralized function to handle unit conversions using a predefined map of unit-to-meter conversion factors.
    - Converts the input unit to meters and then to the target unit, ensuring precision.
- **Dialogs**:
    - A dedicated **Conversion Guide Dialog** displaying all available units and their conversion factors to meters.

---

## **Usage**

### **Step-by-Step Instructions**
1. **Launch the App**: Start the application to arrive at the **Home Screen**.
    - Press the **"Continue"** button to proceed to the main screen.
2. **Enter a Value**:
    - On the **Main Screen**, input the numeric value you want to convert (e.g., "12.5").
3. **Select Units**:
    - Use the dropdown menus to pick the **input unit** (e.g., "inch") and the **output unit** (e.g., "foot").
4. **Perform Conversion**:
    - Tap the **"Convert"** button.
    - The app will display the converted value (e.g., "1.04 feet").
5. **Explore Additional Features**:
    - Use the **Options Menu** in the top-right corner to:
        - Access the **Conversion Guide**.
        - Switch between **Light Mode** and **Dark Mode**.

---

## **Design Principles**

### **Clean Architecture**
- **Modularity**: Organized into separate, reusable components for better maintainability.
- **Separation of Concerns**: Logic for UI, navigation, and unit conversion is well-isolated.

### **Error Handling and Validation**
- Ensures inputs are numeric and non-negative.
- Provides default values for invalid selections to prevent app crashes.

### **Readability and Documentation**
- Code is documented with **KDoc** for classes, methods, and key logic.
- Proper naming conventions and comments make the codebase easy to navigate.

---

## **Requirements**

### **Software Requirements**
- **Android Studio**: Arctic Fox(2021) or later.
- **Kotlin**: Version 1.9 or newer.

### **Hardware Requirements**
- An Android device or emulator with API Level 21 or higher.






