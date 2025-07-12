# Math Formulas App

A comprehensive offline mathematics formulas application built with React Native and Expo, featuring Material You design principles.

## Features

### 📚 Comprehensive Formula Collection
- **Algebra**: Linear equations, quadratic formulas, polynomials, inequalities
- **Geometry**: 2D/3D shapes, areas, volumes, coordinate geometry
- **Trigonometry**: Basic functions, identities, inverse functions
- **Calculus**: Derivatives, integrals, limits, series
- **Statistics**: Descriptive statistics, probability, regression
- **Physics**: Mechanics, thermodynamics, electromagnetism

### 🎨 Material You Design
- Modern, clean interface following Material You guidelines
- Dynamic color system with proper contrast ratios
- Consistent typography and spacing
- Smooth animations and transitions
- Responsive design for different screen sizes

### 🔍 Smart Search & Navigation
- Real-time search across formula names, descriptions, and tags
- Category-based browsing with subcategory filtering
- Difficulty level filtering (Beginner, Intermediate, Advanced)
- Quick access to favorites and recent formulas

### 📖 Detailed Formula Information
- **Formula Display**: Clear, formatted mathematical expressions
- **Tips & Tricks**: Practical advice for using each formula
- **Step-by-step Examples**: Worked problems with explanations
- **Key Concepts**: Fundamental understanding points
- **Tags**: Easy categorization and discovery

### 💾 Offline Functionality
- Complete offline operation using SQLite database
- Local storage of all formulas and user preferences
- Favorites system with persistent storage
- Recent formulas tracking
- No internet connection required

### 🔧 Technical Features
- Built with React Native and Expo
- TypeScript for type safety
- SQLite for local data storage
- React Navigation for smooth navigation
- Expo Vector Icons for consistent iconography

## Getting Started

### Prerequisites
- Node.js (v16 or higher)
- npm or yarn
- Expo CLI (`npm install -g @expo/cli`)
- Android Studio (for Android development)
- Xcode (for iOS development, macOS only)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd MathFormulasApp
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start the development server**
   ```bash
   npm start
   ```

4. **Run on device/simulator**
   - Press `a` for Android
   - Press `i` for iOS
   - Scan QR code with Expo Go app on your phone

### Building for Production

#### Android APK
```bash
expo build:android
```

#### iOS IPA
```bash
expo build:ios
```

## Project Structure

```
MathFormulasApp/
├── src/
│   ├── components/          # Reusable UI components
│   │   ├── Button.tsx
│   │   └── Card.tsx
│   ├── data/               # Static data and formulas
│   │   └── formulas.ts
│   ├── navigation/         # Navigation configuration
│   │   └── AppNavigator.tsx
│   ├── screens/            # App screens
│   │   ├── HomeScreen.tsx
│   │   ├── CategoryScreen.tsx
│   │   ├── FormulaDetailScreen.tsx
│   │   ├── FavoritesScreen.tsx
│   │   └── RecentScreen.tsx
│   ├── services/           # Business logic and data services
│   │   └── DatabaseService.ts
│   ├── theme/              # Design system and theming
│   │   └── index.ts
│   └── types/              # TypeScript type definitions
│       └── index.ts
├── App.tsx                 # Main app component
├── package.json
└── README.md
```

## Database Schema

The app uses SQLite for offline storage with the following tables:

### Formulas Table
- `id`: Unique identifier
- `name`: Formula name
- `formula`: Mathematical expression
- `description`: Detailed description
- `category`: Main category
- `subcategory`: Subcategory (optional)
- `difficulty`: Beginner/Intermediate/Advanced
- `tips`: JSON array of tips
- `examples`: JSON array of examples
- `concepts`: JSON array of key concepts
- `tags`: JSON array of tags

### Favorites Table
- `id`: Auto-incrementing primary key
- `formula_id`: Reference to formula
- `created_at`: Timestamp

### Recent Table
- `id`: Auto-incrementing primary key
- `formula_id`: Reference to formula
- `viewed_at`: Timestamp

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Adding New Formulas

To add new formulas, edit `src/data/formulas.ts`:

```typescript
{
  id: 'unique-formula-id',
  name: 'Formula Name',
  formula: 'mathematical expression',
  description: 'Detailed description',
  category: 'algebra', // Must match existing category
  subcategory: 'Optional Subcategory',
  tips: [
    'Tip 1',
    'Tip 2'
  ],
  examples: [
    {
      problem: 'Example problem',
      solution: 'Step-by-step solution',
      explanation: 'Detailed explanation'
    }
  ],
  concepts: [
    'Key concept 1',
    'Key concept 2'
  ],
  difficulty: 'beginner', // or 'intermediate', 'advanced'
  tags: ['tag1', 'tag2']
}
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Material Design team for design inspiration
- Expo team for the excellent development platform
- React Native community for continuous improvements
- Mathematical community for formula content

## Support

For support, please open an issue in the GitHub repository or contact the development team.