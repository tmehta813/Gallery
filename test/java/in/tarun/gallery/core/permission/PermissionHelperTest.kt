package `in`.tarun.gallery.core.permission

import android.Manifest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PermissionHelperTest {

    @Test
    fun `requiredPermissions returns non-empty array`() {
        // When
        val permissions = PermissionHelper.requiredPermissions()

        // Then
        assertTrue(permissions.isNotEmpty())
    }

    @Test
    fun `requiredPermissions contains storage permissions`() {
        // When
        val permissions = PermissionHelper.requiredPermissions()

        // Then
        // Should contain either READ_EXTERNAL_STORAGE or READ_MEDIA_* permissions
        val hasStoragePermission = permissions.contains(Manifest.permission.READ_EXTERNAL_STORAGE) ||
                permissions.contains(Manifest.permission.READ_MEDIA_IMAGES) ||
                permissions.contains(Manifest.permission.READ_MEDIA_VIDEO)
        assertTrue(hasStoragePermission)
    }

    @Test
    fun `allPermissionsGranted returns true when all permissions are granted`() {
        // Given
        val requiredPermissions = PermissionHelper.requiredPermissions()
        val grantedMap = requiredPermissions.associateWith { true }

        // When
        val result = PermissionHelper.allPermissionsGranted(grantedMap)

        // Then
        assertTrue(result)
    }

    @Test
    fun `allPermissionsGranted returns false when some permissions are not granted`() {
        // Given
        val requiredPermissions = PermissionHelper.requiredPermissions()
        val grantedMap = requiredPermissions.associateWith { false }

        // When
        val result = PermissionHelper.allPermissionsGranted(grantedMap)

        // Then
        assertFalse(result)
    }

    @Test
    fun `allPermissionsGranted returns false when no permissions are granted`() {
        // Given
        val requiredPermissions = PermissionHelper.requiredPermissions()
        val grantedMap = requiredPermissions.associateWith { false }

        // When
        val result = PermissionHelper.allPermissionsGranted(grantedMap)

        // Then
        assertFalse(result)
    }

    @Test
    fun `allPermissionsGranted returns false when grantedMap is empty`() {
        // Given
        val grantedMap = emptyMap<String, Boolean>()

        // When
        val result = PermissionHelper.allPermissionsGranted(grantedMap)

        // Then
        assertFalse(result)
    }
} 