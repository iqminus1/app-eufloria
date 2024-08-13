create unique index if not exists user_email_ui on users (email) where deleted = false;
create unique index if not exists user_username_ui on users (username) where deleted = false;
create unique index if not exists category_name_ui on category (name) where deleted = false and parent_category_id is null;
create unique index if not exists parent_category_name_ui on category (name, parent_category_id) where deleted = false;
create unique index if not exists product_name_ui on product (name, available,category_id) where deleted = false;