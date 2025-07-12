export interface Formula {
  id: string;
  name: string;
  formula: string;
  description: string;
  category: string;
  subcategory?: string;
  tips: string[];
  examples: Example[];
  concepts: string[];
  difficulty: 'beginner' | 'intermediate' | 'advanced';
  tags: string[];
}

export interface Example {
  problem: string;
  solution: string;
  explanation: string;
}

export interface Category {
  id: string;
  name: string;
  icon: string;
  description: string;
  color: string;
  subcategories?: string[];
}

export interface SearchResult {
  formula: Formula;
  matchType: 'name' | 'description' | 'tags';
  relevance: number;
}