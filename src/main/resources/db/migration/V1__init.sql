-- V1__create_books_table.sql
CREATE TABLE IF NOT EXISTS books (
                                     id BIGSERIAL PRIMARY KEY,
                                     book_name VARCHAR(200) NOT NULL,
                                     author_name VARCHAR(120) NOT NULL,
                                     version BIGINT NOT NULL DEFAULT 0,
                                     created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                     updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Уникальный индекс по имени и автору
CREATE UNIQUE INDEX IF NOT EXISTS uk_books_name_author
    ON books (book_name, author_name);

-- Триггер для автоматического обновления поля updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ language 'plpgsql';

DROP TRIGGER IF EXISTS set_timestamp ON books;

CREATE TRIGGER set_timestamp
    BEFORE UPDATE ON books
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();
