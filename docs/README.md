Alexis YVON, Nicolas MARINHO  ; g2_10

# Accès au serveur

Repo : https://github.com/nicomr70/dilemme-prisonnier

- Se placer dans le répertoire racine
- Lancer la commande `mvn clean install`
- Lancer la commande `heroku local`

# Accès au client

Repo : https://github.com/nicomr70/dilemme-prisonnier-front

- Se placer dans le répertoire racine
- Lancer la commande `npm install`
- Lancer la commande `npm start`

# Résumé

Le serveur SpringBoot en Java garde en mémoire les parties du jeu du **Dilemme du prisonnier**.

Celui-ci communique avec des clients React en JavaScript sur la base de requêtes HTTP et d'émissions SSE.

Pour simuler un échange entre deux joueurs, il suffit d'ouvrir deux onglets ou fenêtre dans le navigateur à l'adresse `localhost:3000`.

Le jeu n'est malheureusement pas entièrement fonctionnel pour le moment.