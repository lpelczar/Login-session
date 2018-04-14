CREATE TABLE users (
  userID INTEGER PRIMARY KEY,
  login TEXT,
  password TEXT
);

-- login: johndoe password: doe
-- login: pablo password: escobar
INSERT INTO users VALUES (1, "johndoe", "799ef92a11af918e3fb741df42934f3b568ed2d93ac1df74f1b8d41a27932a6f");
INSERT INTO users VALUES (2, "pablo", "1197019aed67eaf89c3c3ade7788071680c6f267f7084b3e810dcfdb20abfb62");