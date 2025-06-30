package `in`.tarun.gallery.core.permission

import android.Manifest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PermissionHelperTest {

    @Test
    fun `requiredPermissions returns non-empty array`() {
        val permissions = PermissionHelper.requiredPermissions()

        assertTrue(permissions.isNotEmpty())
    }

    @Test
    fun `requiredPermissions contains storage permissions`() {
        val permissions = PermissionHelper.requiredPermissions()
        val hasStoragePermission = permissions.contains(Manifest.permission.READ_EXTERNAL_STORAGE) ||
                permissions.contains(Manifest.permission.READ_MEDIA_IMAGES) ||
                permissions.contains(Manifest.permission.READ_MEDIA_VIDEO)
        assertTrue(hasStoragePermission)
    }

    @Test
    fun `allPermissionsGranted returns true when all permissions are granted`() {
        val requiredPermissions = PermissionHelper.requiredPermissions()
        val grantedMap = requiredPermissions.associateWith { true }

        val result = PermissionHelper.allPermissionsGranted(grantedMap)

        assertTrue(result)
    }

    @Test
    fun `allPermissionsGranted returns false when some permissions are not granted`() {
        val requiredPermissions = PermissionHelper.requiredPermissions()
        val grantedMap = requiredPermissions.associateWith { false }

        val result = PermissionHelper.allPermissionsGranted(grantedMap)

        assertFalse(result)
    }

    @Test
    fun `allPermissionsGranted returns false when no permissions are granted`() {
        val requiredPermissions = PermissionHelper.requiredPermissions()
        val grantedMap = requiredPermissions.associateWith { false }

        val result = PermissionHelper.allPermissionsGranted(grantedMap)

        assertFalse(result)
    }

    @Test
    fun `allPermissionsGranted returns false when grantedMap is empty`() {
        val grantedMap = emptyMap<String, Boolean>()

        val result = PermissionHelper.allPermissionsGranted(grantedMap)

        assertFalse(result)
    }
} 