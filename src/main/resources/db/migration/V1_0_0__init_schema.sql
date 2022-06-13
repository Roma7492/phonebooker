CREATE TABLE IF NOT EXISTS phones (
    id INT NOT NULL AUTO_INCREMENT,
    phone_name VARCHAR(255),
    is_booked BOOLEAN DEFAULT false,
    user_name VARCHAR(255),
    booking_date DATE
);

CREATE TABLE IF NOT EXISTS phono_api_backup (
    id INT NOT NULL AUTO_INCREMENT,
    technology VARCHAR,
    _2g_bands VARCHAR,
    _3g_bands VARCHAR,
    _4g_bands VARCHAR,
    phone_name VARCHAR
)