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