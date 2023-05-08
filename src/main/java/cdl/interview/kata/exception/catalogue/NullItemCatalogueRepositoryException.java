package cdl.interview.kata.exception.catalogue;

/**
 * Custom unchecked exception for handling errors where the ItemCatalogueRepositoryException is null
 */
public class NullItemCatalogueRepositoryException extends RuntimeException {
    public NullItemCatalogueRepositoryException(String errMessage) {
        super(errMessage);
    }
}