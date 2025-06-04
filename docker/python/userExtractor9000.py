import psycopg2
import json

conn = psycopg2.connect(dbname="laplacebazardDB", user="postgres", password="postgres", host="database", port="5432")
cur = conn.cursor()

# cur.execute("INSERT INTO app.Scores (scoreId, score, number) VALUES\
#   ('11111111-1111-1111-1111-111111111111', 4.5, 10),\
#   ('22222222-2222-2222-2222-222222222222', 3.8, 5),\
#   ('33333333-3333-3333-3333-333333333333', 5.0, 15),\
#   ('44444444-4444-4444-4444-444444444444', 4.2, 8),\
#   ('55555555-5555-5555-5555-555555555555', 4.7, 12),\
#   ('66666666-6666-6666-6666-666666666666', 3.5, 3);")

# cur.execute("INSERT INTO app.UsersTypes (userTypeId, userType) VALUES\
#   ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Student'),\
#   ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Professor'),\
#   ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Administrator');")

# cur.execute("INSERT INTO app.Users (userId, firstName, lastName, email, passwordHash, phoneNumber, creationDate, enabled, scoreClientId, userTypeId, scoreSellerId) VALUES\
#   (gen_random_uuid(), 'John', 'Doe', 'john.doe@example.com', 'hashedpassword1', '555-123-4567', '2023-01-15 08:30:00', TRUE, '11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '44444444-4444-4444-4444-444444444444'),\
#   (gen_random_uuid(), 'Jane', 'Smith', 'jane.smith@example.com', 'hashedpassword2', '555-987-6543', '2023-02-20 14:45:00', TRUE, '22222222-2222-2222-2222-222222222222', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '55555555-5555-5555-5555-555555555555'),\
#   (gen_random_uuid(), 'Laplace', 'Admin', 'admin@laplace.com', 'motdepasse', '555-456-7890', '1970-01-01 00:00:01', true, '33333333-3333-3333-3333-333333333333', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '66666666-6666-6666-6666-666666666666');")



cur.execute("SELECT * FROM app.Users")

allData = cur.fetchall()

users = []
for row in allData:
    
    user = {
        "id": row[0],
        "firstName": row[1],
        "lastName": row[2],
        "email": row[3],
        "credentials": [
            {
                "type": "password",
                "value": "a",  # row[4]
                "temporary": True
            }
        ],
        "createdTimestamp": int(row[6].timestamp() * 1000) if row[6] else None,
        "enabled": row[7],
        "username": row[3]
    }
    cur.execute("SELECT userType FROM app.UsersTypes WHERE userTypeId = %s", (row[9],))
    roles = [role[0] for role in cur.fetchall()]
    user["realmRoles"] = roles 
    users.append(user)

output = {
    "realm": "users",
    "users": users,
    "enabled": True

}



with open('/app/docker/data/users.json', 'w') as file:
    json.dump(output, file, indent=2)

with open('/app/docker/data/users.json', 'r') as f1, open('/app/docker/data/realm-export.json', 'r') as f2:
    users_data = json.load(f1)
    site_data = json.load(f2)

site_data["users"] = users_data.get("users", [])

with open('/app/docker/data/import/merged.json', 'w') as outfile:
    json.dump(site_data, outfile, indent=2)
    

cur.close()
conn.close()

print("SUCCESS?")