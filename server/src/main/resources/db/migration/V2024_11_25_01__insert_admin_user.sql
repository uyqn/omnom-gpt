WITH inserted_user AS (
    INSERT INTO users (email, username, password)
    VALUES ('uy.nguyen2@visma.com','uy.nguyen2','$argon2id$v=19$m=16384,t=2,p=1$sUVYaZEp1u53CF+TToJ1wg$VtnF0NZBclXtB6HgMJI3bCHYP50LTabtrTGTQXbbg+0')
    RETURNING id
)

INSERT INTO user_roles (user_id, role)
SELECT id, role
FROM inserted_user, (VALUES ('USER'), ('ADMIN')) AS roles(role);