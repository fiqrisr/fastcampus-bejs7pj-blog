CREATE TABLE post (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    body TEXT NOT NULL,
    slug VARCHAR(100) NOT NULL,
    is_published BOOLEAN DEFAULT FALSE,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at BIGINT NOT NULL,
    update_at BIGINT,
    published_at BIGINT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
