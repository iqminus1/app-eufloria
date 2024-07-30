create unique index if not exists user_email_ui on users(email) where deleted= false;
create unique index if not exists user_username_ui on users(username) where deleted = false;