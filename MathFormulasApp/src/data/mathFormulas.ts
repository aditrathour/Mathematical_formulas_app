export interface MathFormula {
  id: string;
  name: string;
  formula: string;
  description: string;
  concept: string;
  tips: string[];
  examples: string[];
  category: string;
  difficulty: 'beginner' | 'intermediate' | 'advanced';
}

export const mathFormulas: MathFormula[] = [
  // Algebra
  {
    id: 'quadratic-formula',
    name: 'Quadratic Formula',
    formula: 'x = (-b ± √(b² - 4ac)) / 2a',
    description: 'Solves quadratic equations in the form ax² + bx + c = 0',
    concept: 'The quadratic formula provides the exact solutions to any quadratic equation. It uses the coefficients a, b, and c to find the values of x that make the equation true.',
    tips: [
      'Always check if the equation can be factored first - it\'s often faster',
      'The discriminant (b² - 4ac) tells you about the nature of solutions',
      'If discriminant > 0: two real solutions',
      'If discriminant = 0: one real solution (repeated root)',
      'If discriminant < 0: two complex solutions'
    ],
    examples: [
      'Solve x² + 5x + 6 = 0\nAnswer: x = -2 or x = -3',
      'Solve 2x² - 4x + 2 = 0\nAnswer: x = 1 (repeated root)'
    ],
    category: 'Algebra',
    difficulty: 'intermediate'
  },
  {
    id: 'completing-square',
    name: 'Completing the Square',
    formula: 'x² + bx + c = (x + b/2)² - (b/2)² + c',
    description: 'Technique to rewrite quadratic expressions in vertex form',
    concept: 'Completing the square transforms a quadratic expression into a perfect square trinomial plus a constant. This is useful for finding the vertex of a parabola and solving quadratic equations.',
    tips: [
      'Always factor out the coefficient of x² first',
      'Take half of the coefficient of x, square it, and add/subtract',
      'This method is essential for deriving the quadratic formula',
      'Useful for finding maximum/minimum values'
    ],
    examples: [
      'x² + 6x + 5 = (x + 3)² - 9 + 5 = (x + 3)² - 4',
      '2x² + 8x + 3 = 2(x² + 4x) + 3 = 2(x + 2)² - 8 + 3'
    ],
    category: 'Algebra',
    difficulty: 'intermediate'
  },
  {
    id: 'factoring-difference-squares',
    name: 'Difference of Squares',
    formula: 'a² - b² = (a + b)(a - b)',
    description: 'Special factoring pattern for expressions that are perfect squares minus perfect squares',
    concept: 'The difference of squares is a fundamental factoring pattern that appears frequently in algebra. It states that any expression of the form a² - b² can be factored as (a + b)(a - b).',
    tips: [
      'Both terms must be perfect squares',
      'The operation between them must be subtraction',
      'Useful for simplifying complex expressions',
      'Common in trigonometric identities'
    ],
    examples: [
      'x² - 9 = (x + 3)(x - 3)',
      '16x² - 25y² = (4x + 5y)(4x - 5y)',
      'x⁴ - 1 = (x² + 1)(x² - 1) = (x² + 1)(x + 1)(x - 1)'
    ],
    category: 'Algebra',
    difficulty: 'beginner'
  },

  // Geometry
  {
    id: 'pythagorean-theorem',
    name: 'Pythagorean Theorem',
    formula: 'a² + b² = c²',
    description: 'In a right triangle, the square of the hypotenuse equals the sum of squares of the other two sides',
    concept: 'The Pythagorean theorem is one of the most fundamental relationships in geometry. It only applies to right triangles and provides a way to find the length of any side when you know the other two.',
    tips: [
      'Only works for right triangles (90° angle)',
      'The hypotenuse (c) is always the longest side',
      'The hypotenuse is opposite the right angle',
      'Useful for distance calculations in coordinate geometry'
    ],
    examples: [
      'Triangle with sides 3, 4, 5: 3² + 4² = 5² (9 + 16 = 25)',
      'Find hypotenuse: a=6, b=8, c=√(6² + 8²) = √(36 + 64) = √100 = 10'
    ],
    category: 'Geometry',
    difficulty: 'beginner'
  },
  {
    id: 'area-circle',
    name: 'Area of Circle',
    formula: 'A = πr²',
    description: 'Area of a circle with radius r',
    concept: 'The area of a circle is proportional to the square of its radius. This relationship is fundamental in geometry and appears in many real-world applications.',
    tips: [
      'Remember π ≈ 3.14159 or use 22/7 as approximation',
      'The diameter is twice the radius: d = 2r',
      'Circumference is related: C = 2πr',
      'Area units are always squared (cm², m², etc.)'
    ],
    examples: [
      'Circle with radius 5: A = π(5)² = 25π ≈ 78.54 square units',
      'Circle with diameter 10: r = 5, A = π(5)² = 25π'
    ],
    category: 'Geometry',
    difficulty: 'beginner'
  },
  {
    id: 'volume-sphere',
    name: 'Volume of Sphere',
    formula: 'V = (4/3)πr³',
    description: 'Volume of a sphere with radius r',
    concept: 'The volume of a sphere is proportional to the cube of its radius. This formula is essential in physics, engineering, and many scientific applications.',
    tips: [
      'Volume is always in cubic units',
      'The sphere has the maximum volume for a given surface area',
      'Related to surface area: S = 4πr²',
      'Useful for calculating capacity of spherical containers'
    ],
    examples: [
      'Sphere with radius 3: V = (4/3)π(3)³ = (4/3)π(27) = 36π',
      'Sphere with diameter 8: r = 4, V = (4/3)π(4)³ = (4/3)π(64) = 256π/3'
    ],
    category: 'Geometry',
    difficulty: 'intermediate'
  },

  // Trigonometry
  {
    id: 'sine-law',
    name: 'Law of Sines',
    formula: 'a/sin(A) = b/sin(B) = c/sin(C) = 2R',
    description: 'Relates sides and angles of any triangle, where R is the circumradius',
    concept: 'The Law of Sines provides a relationship between the sides of a triangle and the sines of its angles. It\'s useful for solving triangles when you know some angles and sides.',
    tips: [
      'Works for any triangle (not just right triangles)',
      'Useful when you know two angles and one side (AAS)',
      'Useful when you know two sides and one angle (SSA)',
      'Be careful with SSA case - it can have 0, 1, or 2 solutions'
    ],
    examples: [
      'Triangle with A=30°, B=45°, a=10\nFind b: b = 10sin(45°)/sin(30°) = 10(√2/2)/(1/2) = 10√2',
      'Triangle with a=8, b=6, A=60°\nFind B: sin(B) = 6sin(60°)/8 = 6(√3/2)/8 = 3√3/8'
    ],
    category: 'Trigonometry',
    difficulty: 'intermediate'
  },
  {
    id: 'cosine-law',
    name: 'Law of Cosines',
    formula: 'c² = a² + b² - 2ab cos(C)',
    description: 'Generalization of Pythagorean theorem for any triangle',
    concept: 'The Law of Cosines relates the lengths of the sides of a triangle to the cosine of one of its angles. It reduces to the Pythagorean theorem when the angle is 90°.',
    tips: [
      'Useful when you know three sides (SSS)',
      'Useful when you know two sides and included angle (SAS)',
      'Reduces to a² + b² = c² when C = 90°',
      'Can be rearranged to find angles: cos(C) = (a² + b² - c²)/(2ab)'
    ],
    examples: [
      'Triangle with a=5, b=7, C=60°\nc² = 5² + 7² - 2(5)(7)cos(60°) = 25 + 49 - 70(0.5) = 74 - 35 = 39\nc = √39',
      'Triangle with sides 3, 4, 5\ncos(C) = (3² + 4² - 5²)/(2×3×4) = (9 + 16 - 25)/24 = 0\nC = 90°'
    ],
    category: 'Trigonometry',
    difficulty: 'intermediate'
  },
  {
    id: 'double-angle-sine',
    name: 'Double Angle Formula - Sine',
    formula: 'sin(2θ) = 2sin(θ)cos(θ)',
    description: 'Expresses sin(2θ) in terms of sin(θ) and cos(θ)',
    concept: 'Double angle formulas express trigonometric functions of twice an angle in terms of functions of the original angle. They are derived from the angle addition formulas.',
    tips: [
      'Derived from sin(A + B) = sin(A)cos(B) + cos(A)sin(B)',
      'Useful for integration and solving trigonometric equations',
      'Can be used to find exact values of sin(2θ)',
      'Related to power-reduction formulas'
    ],
    examples: [
      'sin(60°) = 2sin(30°)cos(30°) = 2(1/2)(√3/2) = √3/2',
      'sin(2x) = 2sin(x)cos(x)',
      'If sin(θ) = 3/5, then sin(2θ) = 2(3/5)(4/5) = 24/25'
    ],
    category: 'Trigonometry',
    difficulty: 'advanced'
  },

  // Calculus
  {
    id: 'derivative-power-rule',
    name: 'Power Rule for Derivatives',
    formula: 'd/dx(xⁿ) = nxⁿ⁻¹',
    description: 'Derivative of a power function',
    concept: 'The power rule is one of the most fundamental rules of differentiation. It states that the derivative of x raised to any power n is n times x raised to the power n-1.',
    tips: [
      'Works for any real number n (including fractions and negative numbers)',
      'The derivative of a constant is 0',
      'The derivative of x is 1',
      'Use with chain rule for composite functions'
    ],
    examples: [
      'd/dx(x³) = 3x²',
      'd/dx(x⁻²) = -2x⁻³',
      'd/dx(√x) = d/dx(x¹/²) = (1/2)x⁻¹/² = 1/(2√x)',
      'd/dx(5) = 0 (constant)'
    ],
    category: 'Calculus',
    difficulty: 'intermediate'
  },
  {
    id: 'chain-rule',
    name: 'Chain Rule',
    formula: 'd/dx(f(g(x))) = f\'(g(x)) × g\'(x)',
    description: 'Derivative of a composite function',
    concept: 'The chain rule is used to find the derivative of composite functions. It states that the derivative of f(g(x)) is the derivative of the outer function evaluated at the inner function, multiplied by the derivative of the inner function.',
    tips: [
      'Identify the outer and inner functions',
      'Take derivative of outer function, keeping inner function unchanged',
      'Multiply by derivative of inner function',
      'Practice with simple examples first'
    ],
    examples: [
      'd/dx(sin(x²)) = cos(x²) × 2x = 2x cos(x²)',
      'd/dx((x² + 1)³) = 3(x² + 1)² × 2x = 6x(x² + 1)²',
      'd/dx(eˣ²) = eˣ² × 2x = 2xeˣ²'
    ],
    category: 'Calculus',
    difficulty: 'advanced'
  },
  {
    id: 'integration-by-parts',
    name: 'Integration by Parts',
    formula: '∫u dv = uv - ∫v du',
    description: 'Technique for integrating products of functions',
    concept: 'Integration by parts is a technique derived from the product rule for differentiation. It\'s useful for integrating products of functions where one function is easier to differentiate and the other is easier to integrate.',
    tips: [
      'Choose u to be the function that becomes simpler when differentiated',
      'Choose dv to be the function that is easy to integrate',
      'Use LIATE rule: Logarithmic, Inverse trig, Algebraic, Trigonometric, Exponential',
      'May need to apply multiple times'
    ],
    examples: [
      '∫x eˣ dx\nLet u = x, dv = eˣ dx\ndu = dx, v = eˣ\n∫x eˣ dx = xeˣ - ∫eˣ dx = xeˣ - eˣ + C',
      '∫x ln(x) dx\nLet u = ln(x), dv = x dx\ndu = 1/x dx, v = x²/2\n∫x ln(x) dx = (x²/2)ln(x) - ∫(x²/2)(1/x) dx = (x²/2)ln(x) - x²/4 + C'
    ],
    category: 'Calculus',
    difficulty: 'advanced'
  },

  // Statistics
  {
    id: 'mean-formula',
    name: 'Arithmetic Mean',
    formula: 'μ = (Σxᵢ) / n',
    description: 'Average of a set of numbers',
    concept: 'The arithmetic mean is the sum of all values divided by the number of values. It\'s the most common measure of central tendency and represents the "center" of the data.',
    tips: [
      'Sensitive to outliers (extreme values)',
      'Sum of deviations from mean equals zero',
      'Useful for normally distributed data',
      'Can be weighted for different importance of values'
    ],
    examples: [
      'Data: 2, 4, 6, 8, 10\nMean = (2 + 4 + 6 + 8 + 10) / 5 = 30 / 5 = 6',
      'Data: 1, 3, 5, 7, 9, 11\nMean = (1 + 3 + 5 + 7 + 9 + 11) / 6 = 36 / 6 = 6'
    ],
    category: 'Statistics',
    difficulty: 'beginner'
  },
  {
    id: 'standard-deviation',
    name: 'Standard Deviation',
    formula: 'σ = √(Σ(xᵢ - μ)² / n)',
    description: 'Measure of variability or spread in a dataset',
    concept: 'Standard deviation measures how much the values in a dataset deviate from the mean. A low standard deviation indicates that values are close to the mean, while a high standard deviation indicates wide spread.',
    tips: [
      'Always positive or zero',
      'Same units as the original data',
      'About 68% of data falls within ±1σ of mean',
      'About 95% of data falls within ±2σ of mean',
      'About 99.7% of data falls within ±3σ of mean'
    ],
    examples: [
      'Data: 2, 4, 4, 4, 5, 5, 7, 9\nMean = 5\nσ = √[(9+1+1+1+0+0+4+16)/8] = √(32/8) = √4 = 2',
      'For normal distribution: 68% of data between μ-σ and μ+σ'
    ],
    category: 'Statistics',
    difficulty: 'intermediate'
  },
  {
    id: 'binomial-probability',
    name: 'Binomial Probability',
    formula: 'P(X = k) = C(n,k) × pᵏ × (1-p)ⁿ⁻ᵏ',
    description: 'Probability of exactly k successes in n independent trials',
    concept: 'The binomial distribution models the number of successes in a fixed number of independent trials, each with the same probability of success. It\'s fundamental in probability theory and statistics.',
    tips: [
      'C(n,k) = n!/(k!(n-k)!) is the binomial coefficient',
      'p is the probability of success on each trial',
      'Trials must be independent',
      'Each trial has only two outcomes (success/failure)',
      'Expected value = np, Variance = np(1-p)'
    ],
    examples: [
      'Flip coin 10 times, probability of exactly 3 heads:\nP(X=3) = C(10,3) × (0.5)³ × (0.5)⁷ = 120 × 0.125 × 0.0078 ≈ 0.117',
      'Roll die 5 times, probability of exactly 2 sixes:\nP(X=2) = C(5,2) × (1/6)² × (5/6)³ = 10 × (1/36) × (125/216) ≈ 0.161'
    ],
    category: 'Statistics',
    difficulty: 'advanced'
  },

  // Linear Algebra
  {
    id: 'matrix-determinant-2x2',
    name: '2×2 Matrix Determinant',
    formula: 'det(A) = ad - bc',
    description: 'Determinant of a 2×2 matrix [[a,b],[c,d]]',
    concept: 'The determinant is a scalar value that can be computed from a square matrix. It has important geometric interpretations and is used in solving systems of linear equations.',
    tips: [
      'Only defined for square matrices',
      'Determinant of 0 means matrix is not invertible',
      'Geometric interpretation: area/volume scaling factor',
      'Useful for solving systems of linear equations'
    ],
    examples: [
      'Matrix [[3,1],[2,4]]\ndet = (3×4) - (1×2) = 12 - 2 = 10',
      'Matrix [[1,2],[3,6]]\ndet = (1×6) - (2×3) = 6 - 6 = 0 (not invertible)'
    ],
    category: 'Linear Algebra',
    difficulty: 'intermediate'
  },
  {
    id: 'eigenvalue-characteristic',
    name: 'Characteristic Equation',
    formula: 'det(A - λI) = 0',
    description: 'Equation to find eigenvalues of matrix A',
    concept: 'Eigenvalues are special scalars associated with a matrix that represent how the matrix scales vectors in certain directions. The characteristic equation is used to find these eigenvalues.',
    tips: [
      'λ represents the eigenvalue',
      'I is the identity matrix',
      'Degree of equation equals matrix size',
      'Eigenvalues can be real or complex',
      'Sum of eigenvalues equals trace of matrix'
    ],
    examples: [
      'For 2×2 matrix A = [[4,1],[2,3]]\nA - λI = [[4-λ,1],[2,3-λ]]\ndet = (4-λ)(3-λ) - 2 = λ² - 7λ + 10 = 0\nλ = 5 or λ = 2',
      'Eigenvalues are λ = 5 and λ = 2'
    ],
    category: 'Linear Algebra',
    difficulty: 'advanced'
  },

  // Number Theory
  {
    id: 'euclidean-algorithm',
    name: 'Euclidean Algorithm',
    formula: 'gcd(a,b) = gcd(b, a mod b)',
    description: 'Efficient method to find greatest common divisor',
    concept: 'The Euclidean algorithm is an efficient method for computing the greatest common divisor (GCD) of two integers. It\'s based on the principle that the GCD of two numbers also divides their difference.',
    tips: [
      'Continue until remainder is 0',
      'Last non-zero remainder is the GCD',
      'Much faster than prime factorization for large numbers',
      'Can be extended to find Bézout coefficients'
    ],
    examples: [
      'Find gcd(48, 18):\n48 = 2×18 + 12\ngcd(18, 12) = gcd(12, 6) = gcd(6, 0) = 6',
      'Find gcd(252, 105):\n252 = 2×105 + 42\ngcd(105, 42) = gcd(42, 21) = gcd(21, 0) = 21'
    ],
    category: 'Number Theory',
    difficulty: 'intermediate'
  },
  {
    id: 'fermat-little-theorem',
    name: 'Fermat\'s Little Theorem',
    formula: 'aᵖ⁻¹ ≡ 1 (mod p)',
    description: 'If p is prime and a is not divisible by p',
    concept: 'Fermat\'s Little Theorem is a fundamental result in number theory that provides a necessary condition for primality. It\'s used in primality testing and cryptography.',
    tips: [
      'Only works when p is prime',
      'a must not be divisible by p',
      'Useful for primality testing',
      'Foundation for RSA cryptography',
      'Can be used to find modular inverses'
    ],
    examples: [
      'For p=7, a=3: 3⁶ ≡ 1 (mod 7)\n3⁶ = 729\n729 ÷ 7 = 104 remainder 1',
      'Find 2¹⁰ mod 11:\n2¹⁰ = (2⁵)² = 32² = 1024\n1024 mod 11 = 1'
    ],
    category: 'Number Theory',
    difficulty: 'advanced'
  }
];

export const categories = [
  'Algebra',
  'Geometry', 
  'Trigonometry',
  'Calculus',
  'Statistics',
  'Linear Algebra',
  'Number Theory'
];

export const getFormulasByCategory = (category: string): MathFormula[] => {
  return mathFormulas.filter(formula => formula.category === category);
};

export const searchFormulas = (query: string): MathFormula[] => {
  const lowercaseQuery = query.toLowerCase();
  return mathFormulas.filter(formula => 
    formula.name.toLowerCase().includes(lowercaseQuery) ||
    formula.description.toLowerCase().includes(lowercaseQuery) ||
    formula.concept.toLowerCase().includes(lowercaseQuery) ||
    formula.category.toLowerCase().includes(lowercaseQuery)
  );
};