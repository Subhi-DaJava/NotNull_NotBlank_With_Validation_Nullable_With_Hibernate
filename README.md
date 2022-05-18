# NotNull_NotBlank_With_Validation_Nullable_With_Hibernate
## En règle générale, l'annotation `@NotNull` de préférence à l'annotation `@Column(nullable = false)`.
## De cette façon, la validation a lieu avant qu'`Hibernate` n'envoie toute requête `SQL` d'insertion ou de mise à jour à la base de données. 
## De plus, il est généralement préférable de s'appuyer sur les règles standard définies dans le `Bean Validation`, plutôt que de laisser `la base de données` gérer `la logique de validation`. 
## Mais, même si `Hibernate` générer le `schéma` de la base de données, il traduira l'annotation `@NotNull` dans les contraintes de la base de données. 
## Assurons la `propriété hibernate` `validator.apply_to_ddl est définie=true`.
