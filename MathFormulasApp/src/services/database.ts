import * as SQLite from 'expo-sqlite';
import { mathFormulas, MathFormula } from '../data/mathFormulas';

class DatabaseService {
  private db: SQLite.SQLiteDatabase | null = null;

  async initDatabase(): Promise<void> {
    try {
      this.db = await SQLite.openDatabaseAsync('mathformulas.db');
      await this.createTables();
      await this.populateFormulas();
    } catch (error) {
      console.error('Database initialization error:', error);
    }
  }

  private async createTables(): Promise<void> {
    if (!this.db) return;

    await this.db.execAsync(`
      CREATE TABLE IF NOT EXISTS formulas (
        id TEXT PRIMARY KEY,
        name TEXT NOT NULL,
        formula TEXT NOT NULL,
        description TEXT NOT NULL,
        concept TEXT NOT NULL,
        tips TEXT NOT NULL,
        examples TEXT NOT NULL,
        category TEXT NOT NULL,
        difficulty TEXT NOT NULL,
        is_favorite INTEGER DEFAULT 0,
        last_viewed TEXT
      );
    `);

    await this.db.execAsync(`
      CREATE TABLE IF NOT EXISTS user_preferences (
        key TEXT PRIMARY KEY,
        value TEXT NOT NULL
      );
    `);

    await this.db.execAsync(`
      CREATE TABLE IF NOT EXISTS search_history (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        query TEXT NOT NULL,
        timestamp TEXT NOT NULL
      );
    `);
  }

  private async populateFormulas(): Promise<void> {
    if (!this.db) return;

    // Check if formulas already exist
    const result = await this.db.getFirstAsync('SELECT COUNT(*) as count FROM formulas');
    if (result && (result as any).count > 0) return;

    // Insert all formulas
    for (const formula of mathFormulas) {
      await this.db.runAsync(
        `INSERT INTO formulas (id, name, formula, description, concept, tips, examples, category, difficulty) 
         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)`,
        [
          formula.id,
          formula.name,
          formula.formula,
          formula.description,
          formula.concept,
          JSON.stringify(formula.tips),
          JSON.stringify(formula.examples),
          formula.category,
          formula.difficulty
        ]
      );
    }
  }

  async getAllFormulas(): Promise<MathFormula[]> {
    if (!this.db) return [];

    const result = await this.db.getAllAsync('SELECT * FROM formulas ORDER BY category, name');
    return result.map((row: any) => ({
      id: row.id,
      name: row.name,
      formula: row.formula,
      description: row.description,
      concept: row.concept,
      tips: JSON.parse(row.tips),
      examples: JSON.parse(row.examples),
      category: row.category,
      difficulty: row.difficulty
    }));
  }

  async getFormulasByCategory(category: string): Promise<MathFormula[]> {
    if (!this.db) return [];

    const result = await this.db.getAllAsync(
      'SELECT * FROM formulas WHERE category = ? ORDER BY name',
      [category]
    );
    return result.map((row: any) => ({
      id: row.id,
      name: row.name,
      formula: row.formula,
      description: row.description,
      concept: row.concept,
      tips: JSON.parse(row.tips),
      examples: JSON.parse(row.examples),
      category: row.category,
      difficulty: row.difficulty
    }));
  }

  async searchFormulas(query: string): Promise<MathFormula[]> {
    if (!this.db) return [];

    const searchQuery = `%${query}%`;
    const result = await this.db.getAllAsync(
      `SELECT * FROM formulas 
       WHERE name LIKE ? OR description LIKE ? OR concept LIKE ? OR category LIKE ?
       ORDER BY category, name`,
      [searchQuery, searchQuery, searchQuery, searchQuery]
    );

    // Add to search history
    await this.addSearchHistory(query);

    return result.map(row => ({
      id: row.id,
      name: row.name,
      formula: row.formula,
      description: row.description,
      concept: row.concept,
      tips: JSON.parse(row.tips),
      examples: JSON.parse(row.examples),
      category: row.category,
      difficulty: row.difficulty
    }));
  }

  async getFormulaById(id: string): Promise<MathFormula | null> {
    if (!this.db) return null;

    const result = await this.db.getFirstAsync(
      'SELECT * FROM formulas WHERE id = ?',
      [id]
    );

    if (!result) return null;

    // Update last viewed timestamp
    await this.db.runAsync(
      'UPDATE formulas SET last_viewed = ? WHERE id = ?',
      [new Date().toISOString(), id]
    );

    return {
      id: result.id,
      name: result.name,
      formula: result.formula,
      description: result.description,
      concept: result.concept,
      tips: JSON.parse(result.tips),
      examples: JSON.parse(result.examples),
      category: result.category,
      difficulty: result.difficulty
    };
  }

  async toggleFavorite(id: string): Promise<void> {
    if (!this.db) return;

    const result = await this.db.getFirstAsync(
      'SELECT is_favorite FROM formulas WHERE id = ?',
      [id]
    );

    const newFavoriteValue = result ? (result.is_favorite ? 0 : 1) : 1;
    await this.db.runAsync(
      'UPDATE formulas SET is_favorite = ? WHERE id = ?',
      [newFavoriteValue, id]
    );
  }

  async getFavorites(): Promise<MathFormula[]> {
    if (!this.db) return [];

    const result = await this.db.getAllAsync(
      'SELECT * FROM formulas WHERE is_favorite = 1 ORDER BY category, name'
    );
    return result.map(row => ({
      id: row.id,
      name: row.name,
      formula: row.formula,
      description: row.description,
      concept: row.concept,
      tips: JSON.parse(row.tips),
      examples: JSON.parse(row.examples),
      category: row.category,
      difficulty: row.difficulty
    }));
  }

  async getRecentlyViewed(): Promise<MathFormula[]> {
    if (!this.db) return [];

    const result = await this.db.getAllAsync(
      'SELECT * FROM formulas WHERE last_viewed IS NOT NULL ORDER BY last_viewed DESC LIMIT 10'
    );
    return result.map(row => ({
      id: row.id,
      name: row.name,
      formula: row.formula,
      description: row.description,
      concept: row.concept,
      tips: JSON.parse(row.tips),
      examples: JSON.parse(row.examples),
      category: row.category,
      difficulty: row.difficulty
    }));
  }

  async addSearchHistory(query: string): Promise<void> {
    if (!this.db) return;

    await this.db.runAsync(
      'INSERT INTO search_history (query, timestamp) VALUES (?, ?)',
      [query, new Date().toISOString()]
    );
  }

  async getSearchHistory(): Promise<string[]> {
    if (!this.db) return [];

    const result = await this.db.getAllAsync(
      'SELECT DISTINCT query FROM search_history ORDER BY timestamp DESC LIMIT 10'
    );
    return result.map(row => row.query);
  }

  async clearSearchHistory(): Promise<void> {
    if (!this.db) return;

    await this.db.runAsync('DELETE FROM search_history');
  }

  async getUserPreference(key: string): Promise<string | null> {
    if (!this.db) return null;

    const result = await this.db.getFirstAsync(
      'SELECT value FROM user_preferences WHERE key = ?',
      [key]
    );
    return result ? result.value : null;
  }

  async setUserPreference(key: string, value: string): Promise<void> {
    if (!this.db) return;

    await this.db.runAsync(
      'INSERT OR REPLACE INTO user_preferences (key, value) VALUES (?, ?)',
      [key, value]
    );
  }

  async getCategories(): Promise<string[]> {
    if (!this.db) return [];

    const result = await this.db.getAllAsync(
      'SELECT DISTINCT category FROM formulas ORDER BY category'
    );
    return result.map(row => row.category);
  }

  async getDifficultyLevels(): Promise<string[]> {
    if (!this.db) return [];

    const result = await this.db.getAllAsync(
      'SELECT DISTINCT difficulty FROM formulas ORDER BY difficulty'
    );
    return result.map(row => row.difficulty);
  }
}

export const databaseService = new DatabaseService();