import psycopg2
import json

conn = psycopg2.connect(dbname="laplacebazardDB", user="postgres", password="postgres", host="database", port="5432")
cur = conn.cursor()
cur.execute("SELECT * FROM app.Users")

allData = cur.fetchall()

users = []
for row in allData:
    user = {
        "id": row[0],
        "username": row[1],
        "email": row[2],
        "firstName": row[3],
        "lastName": row[4],
        "enabled": True,

        # SAFE
        "credentials": [
            {
                "type": "password",
                "value": "a",
                "temporary": False
            }
        ]
        # "createdTimestamp": "1970-01-01T00:00:00Z", 
    }
    users.append(user)

output = {
    "realm": "users",
    "users": users,
    "enabled": True

}



with open('/app/docker/data/users.json', 'w') as file:
    json.dump(output, file, indent=2)

with open('/app/docker/data/users.json', 'r') as f1, open('/app/docker/data/site.json', 'r') as f2:
    users_data = json.load(f1)
    site_data = json.load(f2)

merged = {
    "realm": users_data.get("realm", "users"),
    "enabled": users_data.get("enabled", True),
    "users": users_data.get("users", []),
    "clients": [site_data]
}

with open('/app/docker/data/import/merged.json', 'w') as outfile:
    json.dump(merged, outfile, indent=2)

outfile.close()

cur.close()
conn.close()

print("SUCCESS?")