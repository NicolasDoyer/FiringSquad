#FiringSquad

"Firing Squad" est un problème de synchronisation.
Le but est de trouver un ensemble de règles permettant aux cellules d'entrer dans l'état "firing" en même temps.

![alt text](./img/7synchronized.svg "7 synchronised") 

## Utilisation

Afficher l'aide:
```sh
java Main --help
```

Lancer un algorithme et retourner son résultat: (L'utilisation de -iteration est optionnelle. L'algorithme utilisera la valeur par défaut présente dans sa classe)
```sh
java Main --[hcfi|randomsearch|ils] [-iteration x]
```

Lancer un benchmark (données sauvegardées dans différents fichiers)
```sh
java Main --benchmark
```

/!\ Le benchmark peut prendre beaucoup de temps (il utilise les 3 algorithmes de recherche). Les données seront sauvegardées dans un dossier benchmark selon le répertoire courant utilisé lors de l'éxéction du programme.
