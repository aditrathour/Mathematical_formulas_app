import * as SQLite from 'expo-sqlite';
import { formulas } from '../data/formulas';
import { Formula } from '../types';

class DatabaseService {
  private db: SQLite.SQLiteDatabase | null = null;

  async initDatabase(): Promise<void> {
    try {
      this.db = await SQLite.openDatabaseAsync('mathformulas.db');
      
      // Create tables
      await this.createTables();
      
      // Insert initial data if tables are empty
      await this.insertInitialData();
    } catch (error) {
      console.error('Error initializing database:', error);
    }
  }

  private async createTables(): Promise<void> {
    if (!this.db) return;

    // Formulas table
    await this.db.execAsync(`
      CREATE TABLE IF NOT EXISTS formulas (
        id TEXT PRIMARY KEY,
        name TEXT NOT NULL,
        formula TEXT NOT NULL,
        description TEXT NOT NULL,
        category TEXT NOT NULL,
        subcategory TEXT,
        difficulty TEXT NOT NULL,
        tips TEXT,
        examples TEXT,
        concepts TEXT,
        tags TEXT
      );
    `);

    // Favorites table
    await this.db.execAsync(`
      CREATE TABLE IF NOT EXISTS favorites (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        formula_id TEXT NOT NULL,
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (formula_id) REFERENCES formulas (id)
      );
    `);

    // Recent table
    await this.db.execAsync(`
      CREATE TABLE IF NOT EXISTS recent (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        formula_id TEXT NOT NULL,
        viewed_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (formula_id) REFERENCES formulas (id)
      );
    `);
  }

  private async insertInitialData(): Promise<void> {
    if (!this.db) return;

    // Check if formulas table is empty
    const result = await this.db.getFirstAsync('SELECT COUNT(*) as count FROM formulas') as { count: number } | null;
    const count = result?.count || 0;

    if (count === 0) {
      // Insert all formulas
      for (const formula of formulas) {
        await this.db.runAsync(
          `INSERT INTO formulas (id, name, formula, description, category, subcategory, difficulty, tips, examples, concepts, tags) 
           VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`,
          [
            formula.id,
            formula.name,
            formula.formula,
            formula.description,
            formula.category,
            formula.subcategory || null,
            formula.difficulty,
            JSON.stringify(formula.tips),
            JSON.stringify(formula.examples),
            JSON.stringify(formula.concepts),
            JSON.stringify(formula.tags)
          ]
        );
      }
    }
  }

  async getAllFormulas(): Promise<Formula[]> {
    if (!this.db) return [];

    try {
      const result = await this.db.getAllAsync('SELECT * FROM formulas ORDER BY name');
      return result.map(this.mapDatabaseFormulaToFormula);
    } catch (error) {
      console.error('Error getting formulas:', error);
      return [];
    }
  }

  async getFormulasByCategory(category: string): Promise<Formula[]> {
    if (!this.db) return [];

    try {
      const result = await this.db.getAllAsync(
        'SELECT * FROM formulas WHERE category = ? ORDER BY name',
        [category]
      );
      return result.map(this.mapDatabaseFormulaToFormula);
    } catch (error) {
      console.error('Error getting formulas by category:', error);
      return [];
    }
  }

  async searchFormulas(query: string): Promise<Formula[]> {
    if (!this.db) return [];

    try {
      const searchQuery = `%${query}%`;
      const result = await this.db.getAllAsync(
        `SELECT * FROM formulas 
         WHERE name LIKE ? OR description LIKE ? OR tags LIKE ?
         ORDER BY name`,
        [searchQuery, searchQuery, searchQuery]
      );
      return result.map(this.mapDatabaseFormulaToFormula);
    } catch (error) {
      console.error('Error searching formulas:', error);
      return [];
    }
  }

  async getFormulaById(id: string): Promise<Formula | null> {
    if (!this.db) return null;

    try {
      const result = await this.db.getFirstAsync(
        'SELECT * FROM formulas WHERE id = ?',
        [id]
      );
      return result ? this.mapDatabaseFormulaToFormula(result) : null;
    } catch (error) {
      console.error('Error getting formula by id:', error);
      return null;
    }
  }

  async addToFavorites(formulaId: string): Promise<void> {
    if (!this.db) return;

    try {
      await this.db.runAsync(
        'INSERT OR IGNORE INTO favorites (formula_id) VALUES (?)',
        [formulaId]
      );
    } catch (error) {
      console.error('Error adding to favorites:', error);
    }
  }

  async removeFromFavorites(formulaId: string): Promise<void> {
    if (!this.db) return;

    try {
      await this.db.runAsync(
        'DELETE FROM favorites WHERE formula_id = ?',
        [formulaId]
      );
    } catch (error) {
      console.error('Error removing from favorites:', error);
    }
  }

  async getFavorites(): Promise<Formula[]> {
    if (!this.db) return [];

    try {
      const result = await this.db.getAllAsync(`
        SELECT f.* FROM formulas f
        INNER JOIN favorites fav ON f.id = fav.formula_id
        ORDER BY fav.created_at DESC
      `);
      return result.map(this.mapDatabaseFormulaToFormula);
    } catch (error) {
      console.error('Error getting favorites:', error);
      return [];
    }
  }

  async isFavorite(formulaId: string): Promise<boolean> {
    if (!this.db) return false;

    try {
      const result = await this.db.getFirstAsync(
        'SELECT COUNT(*) as count FROM favorites WHERE formula_id = ?',
        [formulaId]
      ) as { count: number } | null;
      return (result?.count || 0) > 0;
    } catch (error) {
      console.error('Error checking favorite status:', error);
      return false;
    }
  }

  async addToRecent(formulaId: string): Promise<void> {
    if (!this.db) return;

    try {
      // Remove existing entry if exists
      await this.db.runAsync(
        'DELETE FROM recent WHERE formula_id = ?',
        [formulaId]
      );
      
      // Add new entry
      await this.db.runAsync(
        'INSERT INTO recent (formula_id) VALUES (?)',
        [formulaId]
      );
      
      // Keep only last 50 recent items
      await this.db.runAsync(`
        DELETE FROM recent WHERE id NOT IN (
          SELECT id FROM recent ORDER BY viewed_at DESC LIMIT 50
        )
      `);
    } catch (error) {
      console.error('Error adding to recent:', error);
    }
  }

  async getRecent(): Promise<Formula[]> {
    if (!this.db) return [];

    try {
      const result = await this.db.getAllAsync(`
        SELECT f.* FROM formulas f
        INNER JOIN recent r ON f.id = r.formula_id
        ORDER BY r.viewed_at DESC
        LIMIT 20
      `);
      return result.map(this.mapDatabaseFormulaToFormula);
    } catch (error) {
      console.error('Error getting recent:', error);
      return [];
    }
  }

  private mapDatabaseFormulaToFormula(dbFormula: any): Formula {
    return {
      id: dbFormula.id,
      name: dbFormula.name,
      formula: dbFormula.formula,
      description: dbFormula.description,
      category: dbFormula.category,
      subcategory: dbFormula.subcategory,
      difficulty: dbFormula.difficulty as 'beginner' | 'intermediate' | 'advanced',
      tips: JSON.parse(dbFormula.tips || '[]'),
      examples: JSON.parse(dbFormula.examples || '[]'),
      concepts: JSON.parse(dbFormula.concepts || '[]'),
      tags: JSON.parse(dbFormula.tags || '[]'),
    };
  }
}

export const databaseService = new DatabaseService();