/**
 * Parse a {@code String ingredientName} into a {@code IngredientName}
 * @throws ParseException if the given {@code ingredientName} is invalid.
 */
public static IngredientName parseIngredientName(String ingredientName) throws ParseException {
        requireNonNull(ingredientName);
        String trimmedName = ingredientName.trim();
        if (!IngredientName.isValidName(trimmedName)) {
        throw new ParseException(IngredientName.MESSAGE_NAME_CONSTRAINTS);
        }
        return new IngredientName(trimmedName);
        }