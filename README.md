# Math Formulas - Android App

A comprehensive Android application containing mathematical formulas with detailed explanations, tips, tricks, and examples. Built with modern Android development practices using Jetpack Compose and Material Design 3.

## Features

🧮 **Comprehensive Formula Collection**
- Wide range of mathematical formulas across multiple categories
- Detailed explanations and concepts for each formula
- Tips and tricks for better understanding
- Real-world examples and applications

📱 **Modern UI/UX**
- Material Design 3 implementation
- Minimalistic and clean interface
- Dynamic colors and theming
- Smooth animations and transitions

🔍 **Smart Search & Filter**
- Real-time search functionality
- Filter by categories and difficulty levels
- Popular search suggestions
- Advanced filtering options

⭐ **Favorites System**
- Save favorite formulas for quick access
- Persistent favorites storage
- Easy toggle on/off functionality

📚 **Category Organization**
- Algebra
- Geometry
- Calculus
- Trigonometry
- Statistics
- Physics
- Chemistry
- Linear Algebra
- Differential Equations
- Number Theory
- Probability
- Complex Analysis
- Topology
- Discrete Math

🌙 **Offline Support**
- Complete offline functionality
- All data stored locally using Room database
- No internet connection required

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room (SQLite)
- **Navigation**: Navigation Compose
- **Design System**: Material Design 3
- **Dependency Injection**: Manual DI
- **Coroutines**: Kotlin Coroutines with Flow

## Architecture

The app follows the MVVM architectural pattern with the following components:

```
app/
├── data/
│   ├── Formula.kt (Entity)
│   ├── FormulaDao.kt (Data Access Object)
│   └── FormulaDatabase.kt (Room Database)
├── repository/
│   └── FormulaRepository.kt (Data Repository)
├── viewmodel/
│   └── FormulaViewModel.kt (ViewModel)
├── ui/
│   ├── screens/ (Composable Screens)
│   ├── theme/ (App Theme)
│   └── navigation/ (Navigation Setup)
└── MainActivity.kt (Entry Point)
```

## Screenshots

### Home Screen
- Welcome card with app statistics
- Quick action buttons
- Category grid
- Recent formulas list

### Formula Detail Screen
- Formula display with mathematical notation
- Comprehensive concept explanation
- Tips and tricks section
- Examples and applications
- Variable definitions
- Units information

### Search Screen
- Real-time search functionality
- Popular search suggestions
- Advanced filtering options
- Search results with highlighting

### Favorites Screen
- Saved formulas collection
- Quick access to important formulas
- Easy favorite management

## Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/math-formulas-android.git
```

2. Open the project in Android Studio

3. Build and run the app on your device or emulator

## Requirements

- Android 7.0 (API level 24) or higher
- Android Studio Arctic Fox or later
- Kotlin 1.9.22 or later

## Database Schema

The app uses Room database with the following schema:

```kotlin
@Entity(tableName = "formulas")
data class Formula(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val formula: String,
    val description: String,
    val category: String,
    val subcategory: String?,
    val concept: String,
    val tips: String,
    val tricks: String,
    val examples: String,
    val difficulty: String,
    val tags: String,
    val isFavorite: Boolean = false,
    val mathJaxFormula: String?,
    val variables: String?,
    val units: String?,
    val relatedFormulas: String?
)
```

## Sample Formulas Included

### Algebra
- Quadratic Formula
- Linear Equations
- Exponential Functions
- Logarithmic Functions

### Geometry
- Pythagorean Theorem
- Area and Volume formulas
- Trigonometric relationships
- Coordinate geometry

### Calculus
- Derivatives rules
- Integration formulas
- Limits and continuity
- Series and sequences

### Physics
- Newton's Laws
- Kinematic equations
- Thermodynamics
- Electromagnetic formulas

## Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a feature branch
3. Add your formulas or improvements
4. Submit a pull request

### Adding New Formulas

To add new formulas, modify the `getSampleFormulas()` function in `FormulaDatabase.kt`:

```kotlin
Formula(
    name = "Your Formula Name",
    formula = "Mathematical expression",
    description = "Brief description",
    category = "Category name",
    subcategory = "Subcategory (optional)",
    concept = "Detailed concept explanation",
    tips = "Helpful tips",
    tricks = "Useful tricks",
    examples = "Real examples",
    difficulty = "Easy/Medium/Hard/Expert",
    tags = "searchable, keywords",
    variables = "Variable definitions",
    units = "Unit information"
)
```

## Future Enhancements

- [ ] Math rendering with LaTeX support
- [ ] Formula calculator functionality
- [ ] Step-by-step solution guides
- [ ] Interactive formula graphs
- [ ] Formula sharing capabilities
- [ ] Cloud sync for favorites
- [ ] Quiz mode for formula practice
- [ ] Dark/Light theme toggle
- [ ] Export formulas to PDF
- [ ] Voice search functionality

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For support, email mathformulas@example.com or open an issue on GitHub.

## Acknowledgments

- Material Design 3 guidelines
- Android Jetpack Compose documentation
- Mathematical formulas from various educational sources
- Open source community for inspiration

---

**Built with ❤️ for mathematics students and professionals**