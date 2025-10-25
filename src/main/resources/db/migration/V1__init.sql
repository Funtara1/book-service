create table if not exists books (
                                     id bigserial primary key,
                                     book_name varchar(200) not null,
    author_name varchar(120) not null,
    version bigint not null default 0,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
    );

create unique index if not exists uk_books_name_author on books(book_name, author_name);
