# Lab04 DAA - Rapport

L'application développée est un gestionnaire de notes permettant de créer, afficher et organiser des
notes avec différentes catégories (todo, shopping, travail, famille) et états (à faire, fait).
L'architecture suit le pattern MVVM avec une base de données Room et une RecyclerView pour l'affichage.

# Implémentation Détaillée

## Architecture MVVM
**Choix d'implémentation :**
- **ViewModel** : `NoteViewModel` pour gérer les données entre Repository et UI
- **LiveData** pour l'observation des changements de données
- **Repository** comme couche d'abstraction entre ViewModel et Room

**Justification :**
MVVM permet une séparation claire des responsabilités et prévient les fuites de mémoire. LiveData assure une mise à jour automatique de l'UI quand les données changent.

## Base de Données Room
**Entités :**
```kotlin
@Entity
data class Note(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val type: NoteType,
    val state: NoteState,
    val creationDate: Date
)

@Entity
data class Schedule(
    @PrimaryKey val id: String,
    val noteId: String,
    val scheduledDate: Date
)

data class NoteAndSchedule(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "id",
        entityColumn = "noteId"
    )
    val schedule: Schedule?
)
```

## Fragments et RecyclerView
 
Il y a deux formats d'affichages :
    **Smartphone** : `layout/activity_main.xml` avec la liste des notes
    **Tablette** : `layout-sw600dp/activity_main.xml` avec la liste des notes
                    et un panel de contrôle

La liste des notes est affiché dans un fragment qui admet un **RecyclerView**. Il gère un affichage
dynamique des différentes notes et notes avec schedule de la BDD.

# Tests Effectués

| Test | Description | Validation |
-----------------------------------
| Rotation de l'écran | | OK |

# Questions complémentaires

>6.1 Quelle est la meilleure approche pour sauver, même après la fermeture de l’app, le choix de
l’option de tri de la liste des notes ? Vous justifierez votre réponse et l’illustrez en présentant
le code mettant en œuvre votre approche.

La bonne approche c'est d'utiliser des **SharedPreferences**. C'est une simple représentation clé/valeur
qui est tout à fait adapté pour cette simple donnée. Les SharedPreferences sont persisté et restent
disponibles même après fermeture de l'application.

Room n'est pas nécessaire car la donnée est simple et n'a pas de relation ou une structure complexe *(String)*.

Exemple dans le ViewModel,
```kotlin
class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsManager = SettingsManager(application)
    private val currentSortType = MutableLiveData<String>()

    init {
        currentSortType.value = settingsManager.getSortType()
    }

    fun updateSortType(newSort: String) {
        currentSortType.value = newSort
        settingsManager.saveSortType(newSort)
    }
}
```

A chaque redémarrage, l’app récupère le dernier choix.

>6.2 L’accès à la liste des notes issues de la base de données Room se fait avec une LiveData. Est-
ce que cette solution présente des limites ? Si oui, quelles sont-elles et quelles seraient les
approches mieux adaptées ?

LiveData c'est bien pour afficher une liste qui change en temps réel, mais il y a quand même des limites.
Déjà, il n'y a pas de gestion d'erreur intégrée - si la liste est vide, on ne sait pas si c'est parce
qu'il y a vraiment rien dans la base ou si y a eu un problème lors de la récupération. Ensuite, même
si Room exécute les requêtes en arrière-plan, LiveData reste assez limité pour les opérations asynchrones
complexes. Si l'on veut combiner plusieurs sources de données ou faire des transformations plus poussées,
c'est pas l'idéal.

Comme solution, les coroutines avec Flow seraient mieux adaptées. Flow permet de gérer les erreurs
proprement et offre plus de flexibilité pour les opérations asynchrones, tout en restant réactif comme LiveData.

>6.3 Les notes affichées dans la RecyclerView ne sont pas sélectionnables ni cliquables. Comment
procéderiez-vous si vous souhaitiez proposer une interface permettant de sélectionner une
note pour l’éditer ?

On ajoute un listener dans l'adapter sur l'évenement onClick du ViewHolder,
```kotlin
class NoteAdapter(
    private val onNoteClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    // ...

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) {
            itemView.setOnClickListener {
                onNoteClick(note)
            }
        }
    }
}
```

# Conclusion

L'application répond aux exigences du laboratoire avec une architecture MVVM propre, une base de données
Room fonctionnelle et une interface adaptative *(ViewHolder, RecyclerView, etc)*.