import { Formula, Category } from '../types';

export const categories: Category[] = [
  {
    id: 'algebra',
    name: 'Algebra',
    icon: 'calculator',
    description: 'Basic algebraic operations and equations',
    color: '#FF6B6B',
    subcategories: ['Linear Equations', 'Quadratic Equations', 'Polynomials', 'Inequalities']
  },
  {
    id: 'geometry',
    name: 'Geometry',
    icon: 'square',
    description: 'Shapes, areas, volumes, and geometric properties',
    color: '#4ECDC4',
    subcategories: ['2D Shapes', '3D Shapes', 'Circles', 'Triangles', 'Coordinate Geometry']
  },
  {
    id: 'trigonometry',
    name: 'Trigonometry',
    icon: 'triangle',
    description: 'Trigonometric functions and identities',
    color: '#45B7D1',
    subcategories: ['Basic Functions', 'Identities', 'Inverse Functions', 'Applications']
  },
  {
    id: 'calculus',
    name: 'Calculus',
    icon: 'function',
    description: 'Derivatives, integrals, and limits',
    color: '#96CEB4',
    subcategories: ['Derivatives', 'Integrals', 'Limits', 'Series', 'Applications']
  },
  {
    id: 'statistics',
    name: 'Statistics',
    icon: 'bar-chart',
    description: 'Data analysis and probability',
    color: '#FFEAA7',
    subcategories: ['Descriptive Statistics', 'Probability', 'Inferential Statistics', 'Regression']
  },
  {
    id: 'physics',
    name: 'Physics',
    icon: 'atom',
    description: 'Physical formulas and equations',
    color: '#DDA0DD',
    subcategories: ['Mechanics', 'Thermodynamics', 'Electromagnetism', 'Optics']
  }
];

export const formulas: Formula[] = [
  // Algebra Formulas
  {
    id: 'quadratic-formula',
    name: 'Quadratic Formula',
    formula: 'x = (-b ± √(b² - 4ac)) / 2a',
    description: 'Solution to quadratic equations in the form ax² + bx + c = 0',
    category: 'algebra',
    subcategory: 'Quadratic Equations',
    tips: [
      'Always check if the equation can be factored first',
      'The discriminant (b² - 4ac) tells you about the nature of roots',
      'If discriminant > 0: two real roots',
      'If discriminant = 0: one real root',
      'If discriminant < 0: two complex roots'
    ],
    examples: [
      {
        problem: 'Solve x² - 5x + 6 = 0',
        solution: 'x = (5 ± √(25 - 24)) / 2 = (5 ± 1) / 2\nx = 3 or x = 2',
        explanation: 'Here a=1, b=-5, c=6. Plugging into the formula gives us the solutions.'
      }
    ],
    concepts: [
      'Quadratic equations have degree 2',
      'The ± symbol means there are usually two solutions',
      'The formula works for any quadratic equation in standard form'
    ],
    difficulty: 'intermediate',
    tags: ['quadratic', 'equation', 'roots', 'solving']
  },
  {
    id: 'distance-formula',
    name: 'Distance Formula',
    formula: 'd = √((x₂ - x₁)² + (y₂ - y₁)²)',
    description: 'Distance between two points in a coordinate plane',
    category: 'geometry',
    subcategory: 'Coordinate Geometry',
    tips: [
      'Remember the order: (x₂ - x₁) and (y₂ - y₁)',
      'This is based on the Pythagorean theorem',
      'Works in 2D and 3D (add z-coordinates for 3D)',
      'Always take the positive square root'
    ],
    examples: [
      {
        problem: 'Find distance between (3, 4) and (7, 1)',
        solution: 'd = √((7-3)² + (1-4)²) = √(16 + 9) = √25 = 5',
        explanation: 'Subtract coordinates, square the differences, add them, then take square root.'
      }
    ],
    concepts: [
      'Based on Pythagorean theorem',
      'Measures shortest distance between points',
      'Essential for coordinate geometry'
    ],
    difficulty: 'beginner',
    tags: ['distance', 'coordinates', 'pythagorean', 'geometry']
  },
  {
    id: 'pythagorean-theorem',
    name: 'Pythagorean Theorem',
    formula: 'a² + b² = c²',
    description: 'In a right triangle, the square of the hypotenuse equals the sum of squares of other sides',
    category: 'geometry',
    subcategory: 'Triangles',
    tips: [
      'Only works for right triangles',
      'c is always the hypotenuse (longest side)',
      'a and b are the legs (shorter sides)',
      'The hypotenuse is opposite the right angle'
    ],
    examples: [
      {
        problem: 'Find hypotenuse if legs are 3 and 4',
        solution: 'c² = 3² + 4² = 9 + 16 = 25\nc = √25 = 5',
        explanation: 'Square the legs, add them, then take square root to find hypotenuse.'
      }
    ],
    concepts: [
      'Fundamental theorem of right triangles',
      'Basis for distance formula',
      'Used in many geometric calculations'
    ],
    difficulty: 'beginner',
    tags: ['triangle', 'right angle', 'hypotenuse', 'legs']
  },
  {
    id: 'area-circle',
    name: 'Area of Circle',
    formula: 'A = πr²',
    description: 'Area of a circle with radius r',
    category: 'geometry',
    subcategory: 'Circles',
    tips: [
      'π ≈ 3.14159 (use 3.14 for approximations)',
      'r is the radius (half the diameter)',
      'The formula gives exact area',
      'Units are squared (e.g., cm², m²)'
    ],
    examples: [
      {
        problem: 'Find area of circle with radius 5 cm',
        solution: 'A = π(5)² = 25π ≈ 78.54 cm²',
        explanation: 'Square the radius, multiply by π to get the area.'
      }
    ],
    concepts: [
      'π is the ratio of circumference to diameter',
      'Area increases with square of radius',
      'Fundamental shape in geometry'
    ],
    difficulty: 'beginner',
    tags: ['circle', 'area', 'radius', 'pi']
  },
  {
    id: 'sine-law',
    name: 'Law of Sines',
    formula: 'a/sin(A) = b/sin(B) = c/sin(C) = 2R',
    description: 'Relates sides and angles of any triangle',
    category: 'trigonometry',
    subcategory: 'Basic Functions',
    tips: [
      'Works for any triangle (not just right triangles)',
      'R is the circumradius (radius of circumscribed circle)',
      'Use when you know two angles and one side',
      'Or when you know two sides and one angle'
    ],
    examples: [
      {
        problem: 'In triangle ABC, A=30°, B=45°, a=10. Find b.',
        solution: 'b/sin(45°) = 10/sin(30°)\nb = 10 × sin(45°)/sin(30°) ≈ 14.14',
        explanation: 'Use the proportion to find the unknown side.'
      }
    ],
    concepts: [
      'Generalizes sine function to any triangle',
      'Related to circumscribed circle',
      'Complementary to Law of Cosines'
    ],
    difficulty: 'intermediate',
    tags: ['sine', 'triangle', 'trigonometry', 'angles']
  },
  {
    id: 'derivative-power',
    name: 'Power Rule for Derivatives',
    formula: 'd/dx(xⁿ) = nxⁿ⁻¹',
    description: 'Derivative of a power function',
    category: 'calculus',
    subcategory: 'Derivatives',
    tips: [
      'Bring down the exponent as coefficient',
      'Subtract 1 from the exponent',
      'Works for any real number n',
      'Constant has derivative 0'
    ],
    examples: [
      {
        problem: 'Find d/dx(x³)',
        solution: 'd/dx(x³) = 3x²',
        explanation: 'Bring down 3, subtract 1 from exponent: 3-1=2.'
      }
    ],
    concepts: [
      'Fundamental rule of differentiation',
      'Basis for more complex derivatives',
      'Rate of change of power functions'
    ],
    difficulty: 'intermediate',
    tags: ['derivative', 'power', 'calculus', 'differentiation']
  },
  {
    id: 'mean-formula',
    name: 'Arithmetic Mean',
    formula: 'μ = (Σxᵢ) / n',
    description: 'Average of a set of numbers',
    category: 'statistics',
    subcategory: 'Descriptive Statistics',
    tips: [
      'Add all values, divide by count',
      'Sensitive to outliers',
      'Use for normally distributed data',
      'Symbol μ (mu) for population mean'
    ],
    examples: [
      {
        problem: 'Find mean of 2, 4, 6, 8, 10',
        solution: 'μ = (2+4+6+8+10)/5 = 30/5 = 6',
        explanation: 'Sum all values (30) and divide by count (5).'
      }
    ],
    concepts: [
      'Measure of central tendency',
      'Balance point of data',
      'Most common average used'
    ],
    difficulty: 'beginner',
    tags: ['mean', 'average', 'statistics', 'central tendency']
  },
  {
    id: 'kinetic-energy',
    name: 'Kinetic Energy',
    formula: 'KE = ½mv²',
    description: 'Energy of motion for an object with mass m and velocity v',
    category: 'physics',
    subcategory: 'Mechanics',
    tips: [
      'Energy is always positive',
      'Depends on square of velocity',
      'Units: Joules (J)',
      'Doubling velocity quadruples energy'
    ],
    examples: [
      {
        problem: 'Find KE of 2kg object moving at 3 m/s',
        solution: 'KE = ½(2)(3)² = ½(2)(9) = 9 J',
        explanation: 'Square velocity, multiply by mass, divide by 2.'
      }
    ],
    concepts: [
      'Energy of motion',
      'Conserved in elastic collisions',
      'Related to work-energy theorem'
    ],
    difficulty: 'intermediate',
    tags: ['energy', 'motion', 'physics', 'mechanics']
  },
  {
    id: 'slope-formula',
    name: 'Slope Formula',
    formula: 'm = (y₂ - y₁) / (x₂ - x₁)',
    description: 'Slope of a line passing through two points',
    category: 'algebra',
    subcategory: 'Linear Equations',
    tips: [
      'Rise over run',
      'Positive slope: line goes up',
      'Negative slope: line goes down',
      'Zero slope: horizontal line',
      'Undefined slope: vertical line'
    ],
    examples: [
      {
        problem: 'Find slope through (2,3) and (4,7)',
        solution: 'm = (7-3)/(4-2) = 4/2 = 2',
        explanation: 'Subtract y-coordinates, divide by difference in x-coordinates.'
      }
    ],
    concepts: [
      'Rate of change',
      'Steepness of line',
      'Used in linear equations'
    ],
    difficulty: 'beginner',
    tags: ['slope', 'line', 'linear', 'rate of change']
  },
  {
    id: 'volume-sphere',
    name: 'Volume of Sphere',
    formula: 'V = (4/3)πr³',
    description: 'Volume of a sphere with radius r',
    category: 'geometry',
    subcategory: '3D Shapes',
    tips: [
      'Volume increases with cube of radius',
      '4/3 is approximately 1.33',
      'Units are cubed (e.g., cm³, m³)',
      'Largest volume for given surface area'
    ],
    examples: [
      {
        problem: 'Find volume of sphere with radius 3 cm',
        solution: 'V = (4/3)π(3)³ = (4/3)π(27) = 36π ≈ 113.1 cm³',
        explanation: 'Cube the radius, multiply by π and 4/3.'
      }
    ],
    concepts: [
      'Perfect symmetry in all directions',
      'Optimal shape for volume',
      'Used in many natural phenomena'
    ],
    difficulty: 'intermediate',
    tags: ['sphere', 'volume', '3D', 'geometry']
  }
];