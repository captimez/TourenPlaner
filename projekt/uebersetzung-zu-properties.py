def clear_file(file_path):
    open(file_path, 'w').close()

with open(".\\uebersetzungen.csv") as file:
    first_line = file.readline().strip()
    setup = first_line.split(";")
    setup.remove("propertyname")
    
    second_language = setup[1]  
    
    langs = {}
    count = 1
    for ele in setup:
        langs[ele] = count
        count += 1
    
    for key in langs.keys():
        clear_file(f"src/main/resources/messages_{key}.properties")
    clear_file("src/main/resources/messages.properties")

    for line in file:
        line = line.strip()
        if line and not line.startswith('#'):  
            inhalt = line.split(';')
            for key in langs.keys():
                
                with open(f"src/main/resources/messages_{key}.properties", "a") as new_file:
                    new_file.write(f"{inhalt[0]}={inhalt[langs[key]]}\n")
                
               
                if key == second_language:
                    with open("src/main/resources/messages.properties", "a") as new_file:
                        new_file.write(f"{inhalt[0]}={inhalt[langs[key]]}\n")
